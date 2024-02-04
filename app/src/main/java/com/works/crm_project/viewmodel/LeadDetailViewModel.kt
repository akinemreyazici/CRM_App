package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Lead
import com.works.crm_project.model.LeadById
import com.works.crm_project.model.Parameter
import com.works.crm_project.model.Parameters
import com.works.crm_project.model.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeadDetailViewModel : ViewModel() {


    val LeadService = ApiClient.getClient().create(com.works.crm_project.services.LeadService::class.java)
    val ParameterService = ApiClient.getClient().create(com.works.crm_project.services.ParameterService::class.java)

    private val _listLeadType = MutableLiveData<List<Parameter>>()
    val listLeadTypes: LiveData<List<Parameter>> get() = _listLeadType

    private val _listLeadStatus = MutableLiveData<List<Parameter>>()
    val listLeadStatus: LiveData<List<Parameter>> get() = _listLeadStatus

    private val _listCurrencyType = MutableLiveData<List<Parameter>>()
    val listCurrencyType: LiveData<List<Parameter>> get() = _listCurrencyType

    private val _listPrecedenceType = MutableLiveData<List<Parameter>>()
    val listPrecedenceType: LiveData<List<Parameter>> get() = _listPrecedenceType

    val lead : MutableLiveData<LeadById?> = MutableLiveData()

    fun fetchLeadType() {
        ParameterService.getLeadTypeList().enqueue(object : Callback<Parameters>{
            override fun onResponse(call: Call<Parameters>, response: Response<Parameters>) {
                if (response.body() != null)
                {
                    _listLeadType.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<Parameters>, t: Throwable) {
                Log.e("ErrorLeadAdd",t.message.toString())
            }

        })
    }
    fun fetchLeadStatus() {
        ParameterService.getLeadStatusList().enqueue(object : Callback<Parameters>{
            override fun onResponse(call: Call<Parameters>, response: Response<Parameters>) {
                if (response.body() != null)
                {
                    _listLeadStatus.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<Parameters>, t: Throwable) {
                Log.e("ErrorLeadAdd",t.message.toString())
            }
        })
    }
    fun fetchCurrencyType() {
        ParameterService.getCurrencyTypeList().enqueue(object : Callback<Parameters>{
            override fun onResponse(call: Call<Parameters>, response: Response<Parameters>) {
                if (response.body() != null)
                {
                    _listCurrencyType.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<Parameters>, t: Throwable) {
                Log.e("ErrorLeadAdd",t.message.toString())
            }
        })
    }
    fun fetchPrecedenceType() {
        ParameterService.getPrecedenceList().enqueue(object : Callback<Parameters>{
            override fun onResponse(call: Call<Parameters>, response: Response<Parameters>) {
                if (response.body() != null)
                {
                    _listPrecedenceType.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<Parameters>, t: Throwable) {
                Log.e("ErrorLeadAdd",t.message.toString())
            }
        })
    }

    fun getLeadById(id : Int) {
        LeadService.getLeadById(id).enqueue(object : Callback<LeadById>{
            override fun onResponse(call: Call<LeadById>, response: Response<LeadById>) {
                val data = response.body()
                if (data != null)
                {

                    lead.postValue(data)
                }
            }

            override fun onFailure(call: Call<LeadById>, t: Throwable) {
                Log.e("LeadDetail",t.message.toString())
            }
        })
    }

    fun deleteLead(id : Int) {
        LeadService.deleteLead(id).enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val data = response.body()
                Log.e("LEADDATA",data.toString())
                if (data != null)
                {
                    if (data.success)
                    {
                        Log.e("LeadDetail","SİLME BAŞARILI")
                    }
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}