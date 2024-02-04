package com.works.crm_project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Firm
import com.works.crm_project.model.Firms
import com.works.crm_project.services.FirmService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirmViewModel : ViewModel() {

    var ClientService = ApiClient.getClient().create(FirmService::class.java)


    private val _list = MutableLiveData<List<Firm>>()
    val list: LiveData<List<Firm>> get() = _list


    fun fetchData() {
        ClientService.getFirmList().enqueue(object : Callback<Firms> {
            override fun onResponse(call: Call<Firms>, response: Response<Firms>) {
                val data = response.body()
                if (data != null) {
                    _list.postValue(data.data)

                }
            }

            override fun onFailure(call: Call<Firms>, t: Throwable) {

            }

        })
    }

    fun fetchSupplierData() {
        ClientService.getSupplierFirmList().enqueue(object : Callback<Firms>{
            override fun onResponse(call: Call<Firms>, response: Response<Firms>) {
                val data = response.body()
                if (data != null)
                {
                    _list.postValue(data.data)
                }
            }

            override fun onFailure(call: Call<Firms>, t: Throwable) {

            }

        })
    }

    fun fetchCustomerData() {
        ClientService.getCustomerFirmList().enqueue(object : Callback<Firms>{
            override fun onResponse(call: Call<Firms>, response: Response<Firms>) {
                val data = response.body()
                if (data != null)
                {
                    _list.postValue(data.data)
                }
            }

            override fun onFailure(call: Call<Firms>, t: Throwable) {

            }

        })
    }
}