package com.example.sosapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
//import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException

class Location: AppCompatActivity() {
    private var mLatitudeLabel: String? = null
    private var mLongitudeLabel: String? = null
    private var mLastLocation: Location? = null
    var latitude: String ? = null
    var longitude: String? = null


    private lateinit var lastLocation: Location
    lateinit var getLocation : Location
    private var mFusedLocationClient: FusedLocationProviderClient? = null



    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ...

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
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
    }}