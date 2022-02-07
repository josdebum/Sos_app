//package com.example.sosapp
//
//import android.opengl.ETC1.encodeImage
//import com.google.gson.annotations.SerializedName
//
//fun addDummyUser(image: String, Latitude: String?, longitude: String?) {
//
//    val userInfo = UserInfo(
//        PhoneNumbers= arrayOf("080333333333", "080444444444"),
//        Image = "n h.",
//        Location = LocationDetails("",""))
//
//}
//
//data class UserInfo (
//
//    @SerializedName("phoneNumbers") val PhoneNumbers: Array<String>?,
//    @SerializedName("image") val Image: String?,
//    @SerializedName("location") val Location: LocationDetails
//
//)
//
//class LocationDetails(
//    @SerializedName("latitude") val Latitude: String?,
//    @SerializedName("longitude") val Longitude: String?,
//)
//


package com.example.sosapp

import com.google.gson.annotations.SerializedName

fun addDummyUser(image: String, latitude: String?, longitude: String?): UserInfo {

    return UserInfo(
        phoneNumbers = arrayOf("080333333333", "080444444444"),
        image = image,
        location = LocationDetails(latitude, longitude)
    )
}

data class UserInfo (

    @SerializedName("phoneNumbers") val phoneNumbers: Array<String>?,
    @SerializedName("image") val image: String?,
    @SerializedName("location") val location: LocationDetails



) {
    override fun toString(): String {
        return "UserInfo(phoneNumbers=${phoneNumbers?.contentToString()}, image=$image, location=$location)"
    }
}

 data class LocationDetails(
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?,
)