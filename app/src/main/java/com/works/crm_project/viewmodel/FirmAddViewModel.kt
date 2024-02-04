package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Firm
import com.works.crm_project.model.Parameter
import com.works.crm_project.model.Parameters
import com.works.crm_project.model.PostResponse
import com.works.crm_project.services.FirmService
import com.works.crm_project.services.ParameterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirmAddViewModel : ViewModel() {
    var ClientService = ApiClient.getClient().create(ParameterService::class.java)
    var FirmService = ApiClient.getClient().create(FirmService::class.java)

    private val _listCity = MutableLiveData<List<Parameter>>()
    val listCity: LiveData<List<Parameter>> get() = _listCity

    private val _listFirmAreaActivity = MutableLiveData<List<Parameter>>()
    val listFirmAreaActivity: LiveData<List<Parameter>> get() = _listFirmAreaActivity

    private val _listBusinessType = MutableLiveData<List<Parameter>>()
    val listBusinessType: LiveData<List<Parameter>> get() = _listBusinessType

    fun fetchCityList() {
        ClientService.getCityList().enqueue(object : Callback<Parameters> {
            override fun onResponse(call: Call<Parameters>, response: Response<Parameters>) {
                val data = response.body()
                if (data != null)
                {
                    _listCity.postValue(data.data)
                }
            }

            override fun onFailure(call: Call<Parameters>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchFirmAreaActivityList() {
        ClientService.getFirmAreaActivityList().enqueue(object : Callback<Parameters> {
            override fun onResponse(
                call: Call<Parameters>,
                response: Response<Parameters>
            ) {
                val data = response.body()
                if (data != null)
                {
                    _listFirmAreaActivity.postValue(data.data)
                }
            }

            override fun onFailure(call: Call<Parameters>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchBusinessTypeList() {
        ClientService.getBusinessTypeList().enqueue(object : Callback<Parameters> {
            override fun onResponse(
                call: Call<Parameters>,
                response: Response<Parameters>
            ) {
                val data = response.body()
                if (data != null)
                {
                    _listBusinessType.postValue(data.data)
                }
            }

            override fun onFailure(call: Call<Parameters>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addFirm(firm : Firm)
    {
        Log.e("HASANFirma",firm.name)
        FirmService.addFirm(firm).enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val data = response.body()
                if (data != null)
                {
                    Log.e("FirmaEkle",data.message.toString())
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("FirmaEkle",t.message.toString())
            }
        })
    }

}