package ru.webant.openmeters.scenes.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.util.SparseIntArray
import android.view.*
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_camera.*
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.extensions.isCameraPermissionGranted
import ru.webant.openmeters.extensions.setImageDrawable
import java.io.*
import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class CameraFragment : MvpAppCompatFragment(), CameraView {

    private var cameraId = ""
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private lateinit var imageDimension: Size
    private lateinit var file: File
    private var backgroundHandler: Handler? = null
    private var backgroundThread: HandlerThread? = null
    private var cameraDevice: CameraDevice? = null
    private var isFlashSupported = true

    private var stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {

        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            createCameraPreview()
        }

        override fun onDisconnected(cameraDevice: CameraDevice) {
            cameraDevice.close()
        }

        override fun onError(p0: CameraDevice, p1: Int) {
            val cameraDevice = cameraDevice
            cameraDevice!!.close()
        }
    }

    private var textureListener: TextureView.SurfaceTextureListener = object :
        TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(
            surfaceTexture: SurfaceTexture, i: Int, i1: Int
        ) {
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(
            surfaceTexture: SurfaceTexture, i: Int, i1: Int
        ) {
        }

        override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {}
    }

    @InjectPresenter
    lateinit var presenter: CameraPresenter

    @ProvidePresenter
    fun providePresenter(): CameraPresenter = App.appComponent.provideCameraPresenter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        captureImageView.setOnClickListener {
            takePicture()
        }
        flashLightImageView.setOnClickListener {
            presenter.onFlashLightImageViewClicked()
        }
    }

    override fun changeFlashLightState(isNeedToTurnOn: Boolean) {
        if (isNeedToTurnOn) {
            captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH)
            flashLightImageView.setImageDrawable(requireContext(), R.drawable.ic_flashlight_on)
        } else {
            captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF)
            flashLightImageView.setImageDrawable(requireContext(), R.drawable.ic_flashlight_off)
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON)
        cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null)
    }

    private fun takePicture() {
        if (cameraDevice == null) {
            return
        }
        val manager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val characteristics =
                manager.getCameraCharacteristics(cameraDevice!!.id)
            val jpegSizes =
                characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                    ?.getOutputSizes(ImageFormat.JPEG)

            //Capture image with custom size
            var width = 1920
            var height = 1080
            if (jpegSizes != null && jpegSizes.isNotEmpty()) {
                width = jpegSizes[0].width
                height = jpegSizes[0].height
            }
            val reader =
                ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
            val outputSurface: MutableList<Surface> = ArrayList(2)
            outputSurface.add(reader.surface)
            outputSurface.add(Surface(textureView.surfaceTexture))
            val captureBuilder =
                cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureBuilder.addTarget(reader.surface)
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

            //Check orientation base on device
            val rotation = requireActivity().windowManager.defaultDisplay.rotation
            captureBuilder[CaptureRequest.JPEG_ORIENTATION] = ORIENTATIONS[rotation]
            file = File(
                requireContext().getExternalFilesDir(null)?.absolutePath + "/" + UUID.randomUUID()
                    .toString() + ".jpg"
            )
            val readerListener: ImageReader.OnImageAvailableListener = object :
                ImageReader.OnImageAvailableListener {
                override fun onImageAvailable(imageReader: ImageReader) {
                    var image: Image? = null
                    try {
                        image = reader.acquireLatestImage()
                        val buffer: ByteBuffer = image.planes[0].buffer
                        val bytes = ByteArray(buffer.capacity())
                        buffer.get(bytes)
                        save(bytes)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        run { image?.close() }
                    }
                }

                private fun save(bytes: ByteArray) {
                    var outputStream: OutputStream? = null
                    try {
                        outputStream = FileOutputStream(file)
                        outputStream.write(bytes)
                    } finally {
                        outputStream?.close()
                    }
                }
            }
            reader.setOnImageAvailableListener(readerListener, backgroundHandler)
            val captureListener: CameraCaptureSession.CaptureCallback =
                object : CameraCaptureSession.CaptureCallback() {
                    override fun onCaptureCompleted(
                        session: CameraCaptureSession,
                        request: CaptureRequest,
                        result: TotalCaptureResult
                    ) {
                        super.onCaptureCompleted(session, request, result)
                        Toast.makeText(requireContext(), "Saved $file", Toast.LENGTH_SHORT).show()
                        createCameraPreview()
                    }
                }
            cameraDevice!!.createCaptureSession(
                outputSurface,
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        try {
                            cameraCaptureSession.capture(
                                captureBuilder.build(),
                                captureListener,
                                backgroundHandler
                            )
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {}
                },
                backgroundHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun createCameraPreview() {
        try {
            val texture = textureView.surfaceTexture!!
            texture.setDefaultBufferSize(imageDimension.width, imageDimension.height)
            val surface = Surface(texture)
            captureRequestBuilder =
                cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(surface)
            cameraDevice!!.createCaptureSession(
                listOf(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        if (cameraDevice == null) return
                        this@CameraFragment.cameraCaptureSession = cameraCaptureSession
                        updatePreview()
                    }

                    override fun onConfigureFailed(p0: CameraCaptureSession) {
                        Toast.makeText(requireContext(), "Changed", Toast.LENGTH_SHORT).show()
                    }
                },
                null
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun updatePreview() {
        if (cameraDevice == null) {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }
        captureRequestBuilder.set(
            CaptureRequest.CONTROL_MODE,
            CaptureRequest.CONTROL_MODE_AUTO
        )
        try {
            cameraCaptureSession.setRepeatingRequest(
                captureRequestBuilder.build(),
                null,
                backgroundHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission")
    private fun openCamera() {
        val manager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = manager.cameraIdList[0]
            val characteristics = manager.getCameraCharacteristics(cameraId)
            val map = characteristics[CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP]!!
            imageDimension = map.getOutputSizes(SurfaceTexture::class.java)[0]
            if (isCameraPermissionGranted(requireActivity())) {
                manager.openCamera(cameraId, stateCallback, null)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        if (textureView.isAvailable) {
            openCamera()
        } else {
            textureView.surfaceTextureListener = textureListener
        }
    }


    companion object {
        private val ORIENTATIONS = SparseIntArray(4).apply {
            this.append(Surface.ROTATION_0, 90)
            this.append(Surface.ROTATION_90, 0)
            this.append(Surface.ROTATION_180, 270)
            this.append(Surface.ROTATION_270, 180)
        }
    }
}