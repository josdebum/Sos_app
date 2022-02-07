package com.example.sosapp

import com.google.gson.annotations.SerializedName


fun addDummyUser() {
    var image: String
    var latitude: String
    var longitude: String


    val userInfo = UserInfo(
        PhoneNumbers= arrayOf("080333333333", "080444444444"),
        Image = "base_64",
        Location = LocationDetails("",""))
}

data class UserInfo (

    @SerializedName("phoneNumbers") val PhoneNumbers: Array<String>?,
    @SerializedName("image") val Image: String?,
    @SerializedName("location") val Location: LocationDetails

)

class LocationDetails(
    @SerializedName("latitude") val Latitude: String?,
    @SerializedName("longitude") val Longitude: String?,
)