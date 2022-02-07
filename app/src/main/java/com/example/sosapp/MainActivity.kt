package com.example.sosapp

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.motion.widget.Debug.getLocation

class MainActivity : AppCompatActivity() {

    private lateinit var lastLocation: Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<ImageButton>(R.id.button)
        buttonClick.setOnClickListener {

           // getLocation()
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

    }
}