package com.works.crm_project.model


data class GetAuthResponse (
    val data: UserData?,
    val success: Boolean,
    val message: String?
)

data class PostResponse(
    val success: Boolean,
    val message: String?
)

