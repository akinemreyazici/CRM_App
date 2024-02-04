package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Lead
import com.works.crm_project.model.LeadAdd
import com.works.crm_project.model.Parameter
import com.works.crm_project.model.Parameters
import com.works.crm_project.model.PostResponse
import com.works.crm_project.services.LeadService
import com.works.crm_project.services.ParameterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeadAddViewModel : ViewModel() {

    val ParameterService = ApiClient.getClient().create(ParameterService::class.java)
    val LeadService = ApiClient.getClient().create(LeadService::class.java)

    private val _listLeadType = MutableLiveData<List<Parameter>>()
    val listLeadTypes: LiveData<List<Parameter>> get() = _listLeadType

    private val _listLeadStatus = MutableLiveData<List<Parameter>>()
    val listLeadStatus: LiveData<List<Parameter>> get() = _listLeadStatus

    private val _listCurrencyType = MutableLiveData<List<Parameter>>()
    val listCurrencyType: LiveData<List<Parameter>> get() = _listCurrencyType

    private val _listPrecedenceType = MutableLiveData<List<Parameter>>()
    val listPrecedenceType: LiveData<List<Parameter>> get() = _listPrecedenceType

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

    fun addLead(lead : LeadAdd)
    {
        LeadService.addLead(lead).enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.body() != null)
                {
                    Log.e("LeadAdd",response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("ErrorLeadAdd",t.message.toString())
            }

        })
    }

}