package com.works.crm_project.services


import com.works.crm_project.model.Parameters
import retrofit2.Call
import retrofit2.http.GET

interface ParameterService {

    @GET("Parameter/GetCityList")
    fun getCityList() : Call<Parameters>

    @GET("Parameter/GetFirmAreaActivityList")
    fun getFirmAreaActivityList() : Call<Parameters>

    @GET("Parameter/GetBusinessTypeList")
    fun getBusinessTypeList() : Call<Parameters>

    @GET("Parameter/GetLeadTypes")
    fun getLeadTypeList() : Call<Parameters>

    @GET("Parameter/GetLeadStatus")
    fun getLeadStatusList() : Call<Parameters>

    @GET("Parameter/GetCurrencyType")
    fun getCurrencyTypeList() : Call<Parameters>

    @GET("Parameter/GetPrecedence")
    fun getPrecedenceList() : Call<Parameters>

    @GET("Parameter/GetExpenseCategory")
    fun getExpenseCategoryList() : Call<Parameters>
}