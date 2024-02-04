package com.works.crm_project.services

import com.works.crm_project.model.Auth
import com.works.crm_project.model.GetAuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("Auth/Login")
    fun authLogin(@Body auth: Auth): Call<GetAuthResponse>


}