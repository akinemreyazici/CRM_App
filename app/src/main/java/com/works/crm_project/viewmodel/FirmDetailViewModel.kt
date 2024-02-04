package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Firm
import com.works.crm_project.model.FirmInfoById
import com.works.crm_project.model.Parameter
import com.works.crm_project.model.Parameters
import com.works.crm_project.model.PostResponse
import com.works.crm_project.services.ParameterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirmDetailViewModel : ViewModel() {
    var ClientService = ApiClient.getClient().create(ParameterService::class.java)
    var FirmService = ApiClient.getClient().create(com.works.crm_project.services.FirmService::class.java)

    private val _listCity = MutableLiveData<List<Parameter>>()
    val listCity: LiveData<List<Parameter>> get() = _listCity

    private val _listFirmAreaActivity = MutableLiveData<List<Parameter>>()
    val listFirmAreaActivity: LiveData<List<Parameter>> get() = _listFirmAreaActivity

    private val _listBusinessType = MutableLiveData<List<Parameter>>()
    val listBusinessType: LiveData<List<Parameter>> get() = _listBusinessType

    val firm: MutableLiveData<Firm?> = MutableLiveData()

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

    fun getFirmById(id : Int)
    {
        FirmService.getFirmById(id).enqueue(object  : Callback<FirmInfoById>{
            override fun onResponse(call: Call<FirmInfoById>, response: Response<FirmInfoById>) {
                val data = response.body()
                if (data != null)
                {
                    firm.postValue(data.data)
                }
            }

            override fun onFailure(call: Call<FirmInfoById>, t: Throwable) {
                Log.e("FirmDetailGet",t.message.toString())
            }
        })
    }

    fun updateFirm(firm : Firm)
    {
        Log.e("FİRMADETAY","HASAN")
        FirmService.updateFirm(firm).enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val data = response.body()
                if (data != null)
                {
                    Log.e("firmadetay",data.toString())
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("FirmDetail",t.message.toString())
            }
        })
    }


    fun deleteFirm(id : Int)
    {
       FirmService.deleteFirmById(id).enqueue(object : Callback<PostResponse>{
           override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
               val data = response.body()
               Log.e("FirmDetailSilme",data.toString())
           }

           override fun onFailure(call: Call<PostResponse>, t: Throwable) {
               Log.e("FirmDetail",t.message.toString())
           }
       })
    }
}