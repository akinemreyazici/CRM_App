package com.works.crm_project.services

import com.works.crm_project.model.Firms
import com.works.crm_project.model.Lead
import com.works.crm_project.model.LeadAdd
import com.works.crm_project.model.LeadById
import com.works.crm_project.model.Leads
import com.works.crm_project.model.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LeadService {

    @GET("Lead/GetList")
    fun getLeadList() : Call<Leads>

    @GET("Lead/GetById/{Id}")
    fun getLeadById(@Path("Id") id : Int) : Call<LeadById>

    @POST("Lead/Add")
    fun addLead(@Body lead : LeadAdd) : Call<PostResponse>

    @POST("Lead/Delete/{Id}")
    fun deleteLead(@Path("Id") id : Int) : Call<PostResponse>
}