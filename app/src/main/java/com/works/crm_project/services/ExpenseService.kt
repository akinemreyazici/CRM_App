package com.works.crm_project.services

import com.works.crm_project.model.ExpenseAdd
import com.works.crm_project.model.ExpenseList
import com.works.crm_project.model.Firms
import com.works.crm_project.model.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ExpenseService {

    @POST("Expense/AddExpense")
    fun addExpense(@Body expense : ExpenseAdd) : Call<PostResponse>

    //@POST("Expense/Add")
    //fun addExpenseWithImage : Call<ExpenseAdd>

    @GET("Expense/GetList")
    fun getAllExpense() : Call<ExpenseList>

    @GET("Expense/GetMyExpenses/{userId}")
    fun getMyExpense(@Path("userId") id : Int) : Call<ExpenseList>
}