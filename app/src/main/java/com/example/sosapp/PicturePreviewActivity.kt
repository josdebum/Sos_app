package com.example.sosapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.PictureFormat
import com.otaliastudios.cameraview.demo.MessageView
import com.otaliastudios.cameraview.size.AspectRatio
import java.io.*


class PicturePreviewActivity : AppCompatActivity() {
    lateinit var textView: TextView
     var send: SendTo? = null

    companion object {
        var pictureResult: PictureResult? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_preview)
        val result = pictureResult ?: run {
            finish()
            return
        }
        val imageView = findViewById<ImageView>(R.id.image)

        val captureResolution = findViewById<MessageView>(R.id.nativeCaptureResolution)
        val captureLatency = findViewById<MessageView>(R.id.captureLatency)
        val exifRotation = findViewById<MessageView>(R.id.exifRotation)

        val delay = intent.getLongExtra("delay", 0)
        val ratio = AspectRatio.of(result.size)
        captureLatency.setTitleAndMessage("Approx. latency", "$delay milliseconds")
        captureResolution.setTitleAndMessage("Resolution", "${result.size} ($ratio)")
        exifRotation.setTitleAndMessage("EXIF rotation", result.rotation.toString())

        try {
            result.toBitmap(1000, 1000) { bitmap -> imageView.setImageBitmap(bitmap) }
        } catch (e: UnsupportedOperationException) {
            imageView.setImageDrawable(ColorDrawable(Color.GREEN))
            Toast.makeText(this, "Can't preview this format: " + result.getFormat(), Toast.LENGTH_LONG).show()
        }
//        if (result.isSnapshot) {
//            // Log the real size for debugging reason.
//            val options = BitmapFactory.Options()
//            options.inJustDecodeBounds = true
//            BitmapFactory.decodeByteArray(result.data, 0, result.data.size, options)
//            if (result.rotation % 180 != 0) {
//                Log.e("PicturePreview", "The picture full size is ${result.size.height}x${result.size.width}")
//            } else {
//                Log.e("PicturePreview", "The picture full size is ${result.size.width}x${result.size.height}")
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            pictureResult = null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {

            encodeImage()


            Toast.makeText(this, "Sharing...", Toast.LENGTH_SHORT).show()

            SendTo().sendToServer()


            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun encodeImage() {
        val imageView = findViewById<ImageView>(R.id.image)

  //textView = findViewById<TextView>(R.id.textView)

        imageView.drawable?.let {
           val mBitmap = (it as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        //val bitmap = BitmapFactory.decodeResource(resources, R.drawable.image)
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        val image = Base64.encodeToString(imageBytes, Base64.DEFAULT)


    }}



    }
