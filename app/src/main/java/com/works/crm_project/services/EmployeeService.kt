package com.works.crm_project.services

import com.works.crm_project.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EmployeeService {

    @GET("Employe/GetList")
    fun getEmployeeList(): Call<EmployeeInfo>

    @GET("Employe/GetById/{Id}")
    fun getEmployeeById(@Path("Id") id: Int): Call<EmployeeInfoById>

    @POST("Employe/Add")
    fun addEmployee(@Body employee: Employee): Call<PostResponse>

    @POST("Employe/Update")
    fun updateEmployee(@Body employee: Employee): Call<PostResponse>

    @POST("Employe/Delete/{Id}")
    fun deleteEmployeeById(@Path("Id") id : Int) : Call<PostResponse>

}