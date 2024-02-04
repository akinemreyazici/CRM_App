package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Employee
import com.works.crm_project.model.EmployeeInfoById
import com.works.crm_project.model.PostResponse
import com.works.crm_project.services.EmployeeService
import com.works.crm_project.view.employee.EmployeeDetailFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeDetailViewModel : ViewModel() {

    var ClientService = ApiClient.getClient().create(EmployeeService::class.java)
    val employee: MutableLiveData<Employee?> = MutableLiveData()



    fun getEmployeeById(id : Int)
    {

        ClientService.getEmployeeById(id).enqueue(object : Callback<EmployeeInfoById>{
            override fun onResponse(
                call: Call<EmployeeInfoById>,
                response: Response<EmployeeInfoById>
            ) {
                val data = response.body()
                if (data != null){

                    employee.postValue(data.data)

                }
            }

            override fun onFailure(call: Call<EmployeeInfoById>, t: Throwable) {
                Log.e(TAG,t.message.toString())

            }
        })

    }


    fun setEmployeeById(employee : Employee)
    {
        ClientService.updateEmployee(employee).enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    val message = response.message()
                    Log.e("ÇALIŞŞ", message.toString())
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun deleteEmployeeById(id : Int)
    {
        ClientService.deleteEmployeeById(id).enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val data = response.body()
                Log.e("silme",data.toString())
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {

            }
        })
    }



    companion object{
        private val TAG = EmployeeDetailFragment::class.java.simpleName
    }
}