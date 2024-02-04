package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Employee
import com.works.crm_project.model.EmployeeInfo
import com.works.crm_project.services.EmployeeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeListViewModel : ViewModel() {

    var ClientService = ApiClient.getClient().create(EmployeeService::class.java)


    private val _list = MutableLiveData<List<Employee>>()
    val list: LiveData<List<Employee>> get() = _list
    private val accessToken = MutableLiveData<String?>()

    fun setAccessToken(token: String?) {
        accessToken.value = token
    }



    fun fetchData() {
        val token = accessToken.value

        if (!token.isNullOrBlank()) {
            ApiClient.setAuthToken(token)
            ClientService.getEmployeeList().enqueue(object : Callback<EmployeeInfo> {
                override fun onResponse(
                    call: Call<EmployeeInfo>,
                    response: Response<EmployeeInfo>
                ) {
                    val data = response.body()
                    if (data != null) {
                        _list.postValue(data.data)
                    }
                }

                override fun onFailure(call: Call<EmployeeInfo>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }
            })
        }
    }

    companion object {
        private val TAG = EmployeeListViewModel::class.java.simpleName
    }
}
