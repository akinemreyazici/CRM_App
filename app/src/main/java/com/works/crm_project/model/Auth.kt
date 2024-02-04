package com.works.crm_project.model

import com.google.gson.annotations.SerializedName

data class Auth(
    val Email : String,
    val Password : String
)

data class DataToken (
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("expiration") val expiration: String
)

data class UserData(
    @SerializedName("token") val token: DataToken,
    @SerializedName("userId") val userId: Int
)