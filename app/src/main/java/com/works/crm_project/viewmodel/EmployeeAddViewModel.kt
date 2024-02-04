package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Employee
import com.works.crm_project.model.PostResponse
import com.works.crm_project.services.EmployeeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeAddViewModel : ViewModel() {

    var ClientService = ApiClient.getClient().create(EmployeeService::class.java)
    val employee: MutableLiveData<Employee?> = MutableLiveData()

    fun addEmployee ( employee : Employee){
        ClientService.addEmployee(employee).enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    val message = response.message()
                    Log.e("EmployeeAdd", message.toString())
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("EmployeeAdd",t.message.toString())
            }

        })
    }
}