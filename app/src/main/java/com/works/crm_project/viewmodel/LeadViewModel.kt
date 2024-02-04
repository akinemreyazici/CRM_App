package com.works.crm_project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Lead
import com.works.crm_project.model.Leads
import com.works.crm_project.services.LeadService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeadViewModel : ViewModel() {

    var ClientService = ApiClient.getClient().create(LeadService::class.java)


    private val _list = MutableLiveData<List<Lead>>()
    val list: LiveData<List<Lead>> get() = _list


    fun fetchData() {
        ClientService.getLeadList().enqueue(object : Callback<Leads> {
            override fun onResponse(call: Call<Leads>, response: Response<Leads>) {
                val data = response.body()
                if (data != null) {
                    _list.postValue(data.data)

                }
            }

            override fun onFailure(call: Call<Leads>, t: Throwable) {

            }

        })
    }
}