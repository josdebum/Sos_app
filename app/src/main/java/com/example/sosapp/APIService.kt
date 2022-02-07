
package com.example.sosapp

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/create")
    fun createEmployee(@Body userInfo: UserInfo): Call<UserInfo>

    @Headers("Content-Type: application/json")
    @POST("/posts")
    fun createData(@Field("body") body: String): Call<String>
}
