package com.example.sosapp

import com.google.gson.annotations.SerializedName

//data class UserInfo(
//    val department: String? = null,
//    val phoneNumber: Array<PhoneNumber>? = null
//    val location: Array<Location>? = null
//)
//
//
//
//class SceneDetails(// string variables for our name and job
//    var department:  String, var job: String, var phoneNumber: Array<PhoneNumber>, var location: Array <Location>)
//
//
//data class PhoneNumber(val latitude: String, val longitude: String)
//
//data class Location(val latitude: String, val longitude: String)
//

fun addDummyUser() {

    val userInfo = UserInfo(
        userName = "Alex",
        userEmail = "alex@gmail.com",
        userAge = "32",
        userUid = "164E92FC-D37A-4946-81CB-29DE7EE4B124" )


}


data class UserInfo (

    @SerializedName("user_name") val userName: String?,
    @SerializedName("user_email") val userEmail: String?,
    @SerializedName("user_age") val userAge: String?,
    @SerializedName("user_uid") val userUid: String?
)