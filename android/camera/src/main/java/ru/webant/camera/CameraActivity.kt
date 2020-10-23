package ru.webant.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Size
import android.util.SparseIntArray
import android.view.OrientationEventListener
import android.view.Surface
import android.view.TextureView
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_camera.*
import ru.webant.camera.extensions.setImageDrawable
import ru.webant.mediarecorder.R
import java.io.*
import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.ArrayList

class CameraActivity : AppCompatActivity() {

    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private lateinit var imageDimension: Size
    private lateinit var file: File
    private lateinit var cameraCharacteristics: CameraCharacteristics
    private var cameraId = ""
    private var isFlashLightTurnedOn = false
    private var cameraDevice: CameraDevice? = null
    private var someWidth = 0
    private var someHeight = 0

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

    private var textureListener: TextureView.SurfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, i: Int, i1: Int) {
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(surfaceTexture: SurfaceTexture, i: Int, i1: Int) {
        }

        override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
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

    private fun hideStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun setListeners() {
        captureImageView.setOnClickListener {
            takePicture()
        }
        flashLightImageView.setOnClickListener {
            changeFlashLightState()
        }
    }

    private fun changeFlashLightState() {
        isFlashLightTurnedOn = !isFlashLightTurnedOn
        setFlashLightState()
    }

    private fun setFlashLightState() {
        if (isFlashLightTurnedOn) {
            captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH)
            flashLightImageView.setImageDrawable(this, R.drawable.ic_flashlight_on)
        } else {
            captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF)
            flashLightImageView.setImageDrawable(this, R.drawable.ic_flashlight_off)
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON)
        cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null)
    }

    private fun takePicture() {
        if (cameraDevice == null) {
            return
        }
        val manager = this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
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
            val captureBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureBuilder.addTarget(reader.surface)
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

            //Check orientation base on device
            val rotation = this.windowManager.defaultDisplay.rotation
            captureBuilder[CaptureRequest.JPEG_ORIENTATION] = ORIENTATIONS[getJpegOrientation(rotation)]
            if (isFlashLightTurnedOn) {
                captureBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH)
            } else {
                captureBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF)
            }
            file = File(
                this.getExternalFilesDir(null)?.absolutePath + "/" + UUID.randomUUID()
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
                        Toast.makeText(this@CameraActivity, "Saved $file", Toast.LENGTH_SHORT).show()
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
            captureRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(surface)
            cameraDevice!!.createCaptureSession(
                listOf(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        if (cameraDevice == null) {
                            return
                        }
                        this@CameraActivity.cameraCaptureSession = cameraCaptureSession
                        updatePreview()
                    }

                    override fun onConfigureFailed(p0: CameraCaptureSession) {
                        Toast.makeText(this@CameraActivity, "Changed", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
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
        val sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!

        deviceOrientation = (deviceOrientation + 45) / 90 * 90
        val facingFront = cameraCharacteristics
            .get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT
        if (facingFront) deviceOrientation = -deviceOrientation
        // Todo: Fix screen rotation
        return (sensorOrientation + deviceOrientation + 360) % 360
    }

    @SuppressLint("MissingPermission")
    private fun openCamera() {
        val manager = this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = manager.cameraIdList[0]
            cameraCharacteristics = manager.getCameraCharacteristics(cameraId)
            val displayMetrics = DisplayMetrics()
            this.windowManager.defaultDisplay.getMetrics(displayMetrics)
            someHeight = displayMetrics.heightPixels
            someWidth = displayMetrics.widthPixels
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
        private val ORIENTATIONS = SparseIntArray(4).apply {
            this.append(Surface.ROTATION_0, 90)
            this.append(Surface.ROTATION_90, 0)
            this.append(Surface.ROTATION_180, 270)
            this.append(Surface.ROTATION_270, 180)
        }
    }
}