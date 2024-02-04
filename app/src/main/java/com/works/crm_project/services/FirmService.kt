package com.works.crm_project.services

import com.works.crm_project.model.Firm
import com.works.crm_project.model.FirmInfoById
import com.works.crm_project.model.Firms
import com.works.crm_project.model.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FirmService {

    @GET("Firm/GetAllFirms")
    fun getFirmList() : Call<Firms>

    @GET("Firm/GetFirmById/{Id}")
    fun getFirmById(@Path("Id") id: Int) : Call<FirmInfoById>

    @GET("Firm/GetCustomerFirms")
    fun getCustomerFirmList() : Call<Firms>

    @GET("Firm/GetSupplierFirms")
    fun getSupplierFirmList() : Call<Firms>

    @POST("Firm/AddFirm")
    fun addFirm(@Body firm : Firm) : Call<PostResponse>

    @POST("Firm/UpdateFirm")
    fun updateFirm(@Body firm : Firm) : Call<PostResponse>

    @POST("Firm/DeleteFirm/{Id}")
    fun deleteFirmById(@Path("Id") id: Int) : Call<PostResponse>


}