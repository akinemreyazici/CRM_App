package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Auth
import com.works.crm_project.model.GetAuthResponse
import com.works.crm_project.services.AuthService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    interface LoginListener {
        fun onLoginSuccess(token: String,id : Int)
        fun onLoginFailure(error: String)
    }

    private var loginListener: LoginListener? = null
    var ClientService = ApiClient.getClient().create(AuthService::class.java)

    companion object {
        var token: String? = null
        var id : Int? = null
        var email : String? = null
    }

    fun setLoginListener(listener: LoginListener) {
        loginListener = listener
    }

    fun LoginAsEmployee(email: String, password: String) {
        val auth = Auth(email, password)

        ClientService.authLogin(auth).enqueue(object : Callback<GetAuthResponse> {
            override fun onResponse(call: Call<GetAuthResponse>, response: Response<GetAuthResponse>) {
                val data = response.body()

                if (data?.success == true) {
                    token = data.data?.token?.accessToken
                    id = data.data?.userId
                    loginListener?.onLoginSuccess(token!!, id!!)
                    ApiClient.setAuthToken(token!!)

                } else if (data?.success == false ) {
                    loginListener?.onLoginFailure(data.message.toString())
                    //Log.e("Hasan9",data.message.toString())
                }

            }

            override fun onFailure(call: Call<GetAuthResponse>, t: Throwable) {
                //loginListener?.onLoginFailure(t.message.toString())
                Log.e("HASAN", t.message.toString())
            }

        })

    }



}