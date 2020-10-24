package ru.webant.openmeters.scenes.camera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Size
import android.util.SparseIntArray
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_camera.*
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment
import ru.webant.openmeters.extensions.setImageDrawable
import java.io.*
import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.ArrayList

/** Слишком много андроид зависимостей в комплекте с камерой, очень много времени
 * потребовалось бы для переноса логики в презентер :(
 */
class CameraFragment : BaseFragment(), CameraView {

    override val layoutId = R.layout.fragment_camera
    override var toolbarLayoutId = -1
    override var isNeedToShowBottomNavigationView = false

    @InjectPresenter
    lateinit var presenter: CameraPresenter

    @ProvidePresenter
    fun provideCameraPresenter(): CameraPresenter = App.appComponent.provideCameraPresenter()


    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private lateinit var imageDimension: Size
    private lateinit var file: File
    private lateinit var cameraCharacteristics: CameraCharacteristics
    private var cameraId = ""
    private var isFlashLightTurnedOn = false
    private var cameraDevice: CameraDevice? = null

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

    private var textureListener: TextureView.SurfaceTextureListener =
        object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surfaceTexture: SurfaceTexture,
                i: Int,
                i1: Int
            ) {
                openCamera()
            }

            override fun onSurfaceTextureSizeChanged(
                surfaceTexture: SurfaceTexture,
                i: Int,
                i1: Int
            ) {
            }

            override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {}
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        hideStatusBar()
    }

    override fun onResume() {
        super.onResume()
        if (textureView.isAvailable) {
            openCamera()
        } else {
            textureView.surfaceTextureListener = textureListener
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GET_IMAGES_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            val filePaths = ArrayList<String>()
            val clipData = data?.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    filePaths.add(getFilePathFromUri(uri))
                }
            } else {
                data?.data?.let {
                    filePaths.add(getFilePathFromUri(data.data!!))
                }
            }
            navigateToProcessingFragment(filePaths)
        }
    }

    private fun navigateToProcessingFragment(filePaths: ArrayList<String>) {
        CameraFragmentDirections.openProcessingFragment(filePaths.toTypedArray())
            .let(findNavController()::navigate)
    }

    private fun getFilePathFromUri(uri: Uri): String {
        val wholeID: String = DocumentsContract.getDocumentId(uri)
        val id = wholeID.split(":").toTypedArray()[1]
        val column = arrayOf(MediaStore.Images.Media.DATA)
        val sel: String = MediaStore.Images.Media._ID + "=?"
        val cursor: Cursor? = requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )
        val columnIndex: Int = cursor!!.getColumnIndex(column[0])
        var filePath = ""
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex)
        }
        cursor.close()
        return filePath
    }

    private fun hideStatusBar() {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun setListeners() {
        captureImageView.setOnClickListener {
            takePicture()
        }
        flashLightImageView.setOnClickListener {
            changeFlashLightState()
        }
        leftArrowImageView.setOnClickListener {
            presenter.onLeftArrowClicked()
        }
        getImageImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type =
                "image/*" //allows any image file type. Change * to specific extension to limit it
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                REQUEST_GET_IMAGES_FROM_GALLERY
            )
        }
    }

    private fun changeFlashLightState() {
        isFlashLightTurnedOn = !isFlashLightTurnedOn
        setFlashLightState()
    }

    private fun setFlashLightState() {
        if (isFlashLightTurnedOn) {
            captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH)
            flashLightImageView.setImageDrawable(requireActivity(), R.drawable.ic_flashlight_on)
        } else {
            captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF)
            flashLightImageView.setImageDrawable(requireActivity(), R.drawable.ic_flashlight_off)
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
            cameraCharacteristics = manager.getCameraCharacteristics(cameraDevice!!.id)
            val jpegSizes = cameraCharacteristics
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                ?.getOutputSizes(ImageFormat.JPEG)

            //Capture image with custom size
            var width = 1920
            var height = 1080
            if (jpegSizes != null && jpegSizes.isNotEmpty()) {
                width = jpegSizes[0].width
                height = jpegSizes[0].height
            }
            val reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
            val outputSurface: MutableList<Surface> = ArrayList(2)
            outputSurface.add(reader.surface)
            outputSurface.add(Surface(textureView.surfaceTexture))
            val captureBuilder =
                cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureBuilder.addTarget(reader.surface)
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

            //Check orientation base on device
            val rotation = requireActivity().windowManager.defaultDisplay.rotation
            captureBuilder[CaptureRequest.JPEG_ORIENTATION] =
                ORIENTATIONS[getJpegOrientation(rotation)]
            if (isFlashLightTurnedOn) {
                captureBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH)
            } else {
                captureBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF)
            }
            file = File(
                requireActivity().getExternalFilesDir(null)?.absolutePath + "/" + UUID.randomUUID()
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
            reader.setOnImageAvailableListener(readerListener, null)
            val captureListener: CameraCaptureSession.CaptureCallback =
                object : CameraCaptureSession.CaptureCallback() {
                    override fun onCaptureCompleted(
                        session: CameraCaptureSession,
                        request: CaptureRequest,
                        result: TotalCaptureResult
                    ) {
                        super.onCaptureCompleted(session, request, result)
                        val filePath = arrayListOf(file.absolutePath)
                        navigateToProcessingFragment(filePath)
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
                                null
                            )
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {}
                },
                null
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun createCameraPreview() {
        try {
            val texture = textureView.surfaceTexture!!
            texture.setDefaultBufferSize(1920, 1080)
            val surface = Surface(texture)
            captureRequestBuilder =
                cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(surface)
            cameraDevice!!.createCaptureSession(
                listOf(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        if (cameraDevice == null) {
                            return
                        }
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
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO)
        try {
            cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun getJpegOrientation(orientation: Int): Int {
        var deviceOrientation = orientation
        if (deviceOrientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
            return 0
        }
        val sensorOrientation =
            cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!

        deviceOrientation = (deviceOrientation + 45) / 90 * 90
        val facingFront = cameraCharacteristics
            .get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT
        if (facingFront) deviceOrientation = -deviceOrientation
        // Todo: Fix screen rotation
        return (sensorOrientation + deviceOrientation + 360) % 360
    }

    @SuppressLint("MissingPermission")
    private fun openCamera() {
        val manager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = manager.cameraIdList[0]
            cameraCharacteristics = manager.getCameraCharacteristics(cameraId)
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            val map = cameraCharacteristics[CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP]!!
            imageDimension = map.getOutputSizes(SurfaceTexture::class.java)[0]
            // Todo: Permissions in another activity
//            if (isCameraPermissionGranted(this)) {
            manager.openCamera(cameraId, stateCallback, null)
//            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }


    companion object {
        const val RESULT_CODE_SUCCESS = 1009
        const val STRING_ARRAY_LIST_EXTRA = "StringArrayListExtra"

        private const val REQUEST_GET_IMAGES_FROM_GALLERY = 5052
        private val ORIENTATIONS = SparseIntArray(4).apply {
            this.append(Surface.ROTATION_0, 90)
            this.append(Surface.ROTATION_90, 0)
            this.append(Surface.ROTATION_180, 270)
            this.append(Surface.ROTATION_270, 180)
        }
    }
}