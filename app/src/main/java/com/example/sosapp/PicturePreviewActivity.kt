
package com.example.sosapp

import RestApiService
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.*

import com.otaliastudios.cameraview.PictureResult
import java.io.*


class PicturePreviewActivity : AppCompatActivity() {


    val PERMISSION_ID = 42

    lateinit var imageString : String

    private lateinit var mLastLocation: Location
 var locationLatitude: String ? = null
    var locationLongitude: String? = null


    private var mFusedLocationClient: FusedLocationProviderClient? = null

 //lateinit var mfusedLocationClient: FusedLocationProviderClient



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
            encodeImage()
            getLastLocation()
            sendDummySOS()

            Toast.makeText(this, "Scene details sent successfully " , Toast.LENGTH_LONG).show()


            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun encodeImage() {
        Log.e("PicturePreviewActivity", "encodeImage: called")
        val imageView = findViewById<ImageView>(R.id.image)

        imageView.drawable?.let {
            val mBitmap = (it as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val imageBytes = baos.toByteArray()
            imageString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)

        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {

//        if (checkPermissions()) {
//            if (isLocationEnabled()) {

        Log.e("PicturePreviewActivity", "getlastlocation: called")

        mFusedLocationClient?.lastLocation
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result
                    locationLatitude = (mLastLocation).latitude.toString()
                    locationLongitude= (mLastLocation).longitude.toString()

                    Log.e ("", locationLatitude!!)

                } else {
                    Log.w(ContentValues.TAG, "getLastLocation:exception", task.exception)
                    //showMessage(getString(R.string.no_location_detected))
                }
            }

//                mFusedLocationClient?.lastLocation?.addOnCompleteListener(this) { task ->
//                    var location: Location? = task.result
//                    if (location == null) {
//                        requestNewLocationData()
//                    } else {
//                        locationLatitude = location.latitude.toString()
//                        locationLongitude = location.longitude.toString()
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
//        } else {
//            requestPermissions()
//        }
//    }
           // }}}

//private fun requestPermissions() {
//    ActivityCompat.requestPermissions(
//        this,
//        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
//        PERMISSION_ID
//    )
//}
//
//
//    private fun isLocationEnabled(): Boolean {
//        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
//    }
//
//    private fun checkPermissions(): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            return true
//        }
//        return false
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun requestNewLocationData() {
//        var mLocationRequest = LocationRequest()
//        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        mLocationRequest.interval = 0
//        mLocationRequest.fastestInterval = 0
//        mLocationRequest.numUpdates = 1
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        Looper.myLooper()?.let {
//            mFusedLocationClient!!.requestLocationUpdates(
//                mLocationRequest, mLocationCallback,
//                it
//            )
//        }
//    }
//    private val mLocationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            var mLastLocation: Location = locationResult.lastLocation
//            locationLatitude = mLastLocation.latitude.toString()
//            locationLongitude = mLastLocation.longitude.toString()
//        }
   }

    private fun sendDummySOS() {
        Log.e("PicturePreviewActivity", "sendDummySOS: Called")
        val apiService = RestApiService()

        val userInfo = UserInfo(phoneNumbers = arrayOf("090909090", "090909090"),
            image = imageString, location =  LocationDetails(latitude = locationLatitude, longitude = locationLongitude))
//        Log.e("PicturePreviewActivity", "sendDummySOS: userInfo $userInfo")
        Log.e("PicturePreviewActivity", "sendDummySOS: userInfo ${userInfo.phoneNumbers?.get(1)}")
        Log.e("PicturePreviewActivity", "sendDummySOS: userInfo ${userInfo.location.latitude}")
        Log.e("PicturePreviewActivity", "sendDummySOS: userInfo ${userInfo.location.longitude}")
//        Log.e("PicturePreviewActivity", "sendDummySOS: userInfo ${userInfo.image}")
        apiService.sendSOS(userInfo)

    }


    }
