package com.example.sosapp

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.otaliastudios.cameraview.PictureResult
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

//        val captureResolution = findViewById<MessageView>(R.id.nativeCaptureResolution)
//        val captureLatency = findViewById<MessageView>(R.id.captureLatency)
//        val exifRotation = findViewById<MessageView>(R.id.exifRotation)

        val delay = intent.getLongExtra("delay", 0)
        val ratio = AspectRatio.of(result.size)


        try {
            result.toBitmap(1000, 1000) { bitmap -> imageView.setImageBitmap(bitmap) }
        } catch (e: UnsupportedOperationException) {
            imageView.setImageDrawable(ColorDrawable(Color.GREEN))
            Toast.makeText(this, "Can't preview this format: " + result.getFormat(), Toast.LENGTH_LONG).show()
        }
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



            Toast.makeText(this, "Scene details sent successfully", Toast.LENGTH_SHORT).show()

          EncodeImage()
            SendTo()

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun EncodeImage() {
        val image: String

        val imageView = findViewById<ImageView>(R.id.image)

        imageView.drawable?.let {
           val mBitmap = (it as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
            image = Base64.encodeToString(imageBytes, Base64.DEFAULT)


       //  image = addDummyUser(image = )


    }



    }



    }
