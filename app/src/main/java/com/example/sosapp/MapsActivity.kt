package com.example.sosapp

import RestApiService
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.pm.PackageManager

import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class Location: AppCompatActivity() {

    private var mLastLocation: Location? = null
    var latitude: String ? = null
    var longitude: String? = null

    private var mFusedLocationClient: FusedLocationProviderClient? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


    }

    public override fun onStart() {
        super.onStart()

        getLastLocation()
    }


    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result

                   latitude = (mLastLocation )!!.latitude.toString()
                    longitude = (mLastLocation )!!.longitude.toString()

                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                    //showMessage(getString(R.string.no_location_detected))
                }
            }
    }

     fun sendLocationSOS() {
        Log.e("PicturePreviewActivity", "sendDummySOS: Called")
        val apiService = RestApiService()
        val location =  LocationDetails(latitude = latitude, longitude = longitude )

        apiService.sendLocation()
//        apiService.postDummy()
    }


}