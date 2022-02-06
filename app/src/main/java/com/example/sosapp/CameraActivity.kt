package com.example.sosapp


import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.os.Bundle
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity

import com.otaliastudios.cameraview.*
import com.otaliastudios.cameraview.frame.Frame
import com.otaliastudios.cameraview.frame.FrameProcessor
import java.io.ByteArrayOutputStream


class CameraActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val LOG = CameraLogger.create("DemoApp")
        private const val USE_FRAME_PROCESSOR = false
        private const val DECODE_BITMAP = false
    }
    private val camera: CameraView by lazy { findViewById(R.id.camera_button) }
    private var captureTime: Long = 0
    private val controlPanel: ViewGroup by lazy { findViewById(R.id.controls) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        CameraLogger.setLogLevel(CameraLogger.LEVEL_VERBOSE)
        camera.setLifecycleOwner(this)
        camera.addCameraListener(Listener())

        if (USE_FRAME_PROCESSOR) {
            camera.addFrameProcessor(object : FrameProcessor {
                private var lastTime = System.currentTimeMillis()
                override fun process(frame: Frame) {
                    val newTime = frame.time
                    val delay = newTime - lastTime
                    lastTime = newTime
                    LOG.v("Frame delayMillis:", delay, "FPS:", 1000 / delay)
                    if (DECODE_BITMAP) {
                        if (frame.format == ImageFormat.NV21
                            && frame.dataClass == ByteArray::class.java) {
                            val data = frame.getData<ByteArray>()
                            val yuvImage = YuvImage(data,
                                frame.format,
                                frame.size.width,
                                frame.size.height,
                                null)
                            val jpegStream = ByteArrayOutputStream()
                            yuvImage.compressToJpeg(
                                Rect(0, 0,
                                frame.size.width,
                                frame.size.height), 100, jpegStream)
                            val jpegByteArray = jpegStream.toByteArray()
                            val bitmap = BitmapFactory.decodeByteArray(jpegByteArray,
                                0, jpegByteArray.size)
                            bitmap.toString()
                        }
                    }
                }
            })
        }



        findViewById<View>(R.id.edit).setOnClickListener(this)
        findViewById<View>(R.id.capturePicture).setOnClickListener(this)
        findViewById<View>(R.id.capturePictureSnapshot).setOnClickListener(this)
        findViewById<View>(R.id.captureVideo).setOnClickListener(this)
        findViewById<View>(R.id.captureVideoSnapshot).setOnClickListener(this)
        findViewById<View>(R.id.toggleCamera).setOnClickListener(this)
        findViewById<View>(R.id.changeFilter).setOnClickListener(this)
    }

    private inner class Listener : CameraListener() {
        override fun onCameraOpened(options: CameraOptions) {

            val group = controlPanel.getChildAt(0) as ViewGroup
            for (i in 0 until group.childCount) {
                val view = group.getChildAt(i) as OptionView<*>
                view.onCameraOpened(camera, options)
            }

        }

        override fun onCameraError(exception: CameraException) {
            //super.onCameraError(exception)

        }

        override fun onPictureTaken(result: PictureResult) {
            //super.onPictureTaken(result)
            super.onPictureTaken(result)

            val callbackTime = System.currentTimeMillis()
            if (captureTime == 0L) captureTime = callbackTime - 300
            LOG.w("onPictureTaken called! Launching activity. Delay:", callbackTime - captureTime)
            PicturePreviewActivity.pictureResult = result
            val intent = Intent(this@CameraActivity, PicturePreviewActivity::class.java)
            intent.putExtra("delay", callbackTime - captureTime)
            startActivity(intent)
            captureTime = 0
            LOG.w("onPictureTaken called! Launched activity.")


        }

    }

    override fun onClick(view: View) {
        when (view.id) {

            R.id.capturePictureSnapshot -> capturePictureSnapshot()

            //R.id.changeFilter -> changeCurrentFilter()
        }
    }



    private fun capturePictureSnapshot() {
        if (camera.isTakingPicture) return
//                if (camera.preview != Preview.GL_SURFACE) return run {
//                    message("Picture snapshots are only allowed with the GL_SURFACE preview.", true)
//                }
        captureTime = System.currentTimeMillis()
        //message("Capturing picture snapshot...", false)
        camera.takePictureSnapshot()
    }





}
