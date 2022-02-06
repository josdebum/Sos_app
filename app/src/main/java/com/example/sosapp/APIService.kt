package com.example.sosapp

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {

    @POST("/api/v1/create")
    suspend fun sendSceneDetails(@Body requestBody: RequestBody): Response<ResponseBody>

}






