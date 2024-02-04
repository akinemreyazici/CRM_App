package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Expense
import com.works.crm_project.model.ExpenseAdd
import com.works.crm_project.model.Parameter
import com.works.crm_project.model.Parameters
import com.works.crm_project.model.PostResponse
import com.works.crm_project.services.ExpenseService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpenseAddViewModel : ViewModel() {

    val ParameterService = ApiClient.getClient().create(com.works.crm_project.services.ParameterService::class.java)
    val ExpenseService = ApiClient.getClient().create(ExpenseService::class.java)

    private val _listExpenseCategory = MutableLiveData<List<Parameter>>()
    val listExpenseCategory: LiveData<List<Parameter>> get() = _listExpenseCategory

    fun fetchExpenseCategory()
    {
        ParameterService.getExpenseCategoryList().enqueue(object : Callback<Parameters>{
            override fun onResponse(call: Call<Parameters>, response: Response<Parameters>) {
                val data = response.body()
                if (data != null)
                {
                    _listExpenseCategory.postValue(data.data)
                }
            }

            override fun onFailure(call: Call<Parameters>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addExpense(expense : ExpenseAdd)
    {
        ExpenseService.addExpense(expense).enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val data = response.body()
                if (data != null)
                {
                    if (data.success)
                    {
                        Log.e("ExpenseEkle",data.message.toString())
                    }
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("ExpenseEkle",t.message.toString())
            }

        })
    }
}