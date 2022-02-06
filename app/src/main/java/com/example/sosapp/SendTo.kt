package com.example.sosapp

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit


class SendTo () {

    fun sendToServer (){
        println("Send to server called")

        val image : String
    // Create Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("https://dummy.restapiexample.com")
        .build()

    // Create Service
    val service = retrofit.create(APIService::class.java)

    // Create JSON using JSONObject

//    val locationObject = JSONObject()
//    locationObject.put ("latitude", "90")
//    locationObject.put ("longitude", "67")// Host object

    val jsonObject = JSONObject()
    jsonObject.put("phoneNumbers", "080333333333")
    jsonObject.put("location",  "65")

    // Convert JSONObject to String
    val jsonObjectString = jsonObject.toString()

    // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
    val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

    CoroutineScope(Dispatchers.IO).launch {

        Log.e ("Error", jsonObjectString)
        // Do the POST request and get response
        val response = service.sendSceneDetails(requestBody)

        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
      Log.e("Pretty Printed JSON :", response.message())
                print ("I am done")

            } else {
                Log.e("Error Printed:", response.body().toString())

                Log.e("RETROFIT_ERROR", response.code().toString())

            }
        }
    }
}}