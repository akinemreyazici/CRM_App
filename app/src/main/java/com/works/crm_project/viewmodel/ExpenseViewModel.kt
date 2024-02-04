package com.works.crm_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.works.crm_project.configs.ApiClient
import com.works.crm_project.model.Expense
import com.works.crm_project.model.ExpenseList
import com.works.crm_project.model.Firm
import com.works.crm_project.model.Parameter
import com.works.crm_project.model.Parameters
import com.works.crm_project.services.ExpenseService
import com.works.crm_project.services.ParameterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpenseViewModel : ViewModel() {

    val ExpenseService = ApiClient.getClient().create(ExpenseService::class.java)
    val ParameterService = ApiClient.getClient().create(ParameterService::class.java)

    private val _listAll = MutableLiveData<List<Expense>>()
    val listAll: LiveData<List<Expense>> get() = _listAll

    private val _listMy = MutableLiveData<List<Expense>>()
    val listMy: LiveData<List<Expense>> get() = _listMy

    private val _listExpenseCategory = MutableLiveData<List<Parameter>>()
    val listExpenseCategory: LiveData<List<Parameter>> get() = _listExpenseCategory

    fun fetchAllExpense()
    {
        ExpenseService.getAllExpense().enqueue(object : Callback<ExpenseList>{
            override fun onResponse(call: Call<ExpenseList>, response: Response<ExpenseList>) {
                val data = response.body()
                if (data != null)
                {
                    _listAll.postValue(data.data)
                }
            }

            override fun onFailure(call: Call<ExpenseList>, t: Throwable) {
                Log.e("ExpenseViewmodelAllExpense",t.message.toString())
            }
        })
    }

    fun fetchMyExpenses(id : Int)
    {
        ExpenseService.getMyExpense(id).enqueue(object : Callback<ExpenseList>{
            override fun onResponse(call: Call<ExpenseList>, response: Response<ExpenseList>) {
                val data = response.body()
                if (data != null)
                {
                    _listMy.postValue(data.data)
                }
            }

            override fun onFailure(call: Call<ExpenseList>, t: Throwable) {
                Log.e("ExpenseViewmodelMyExpense",t.message.toString())
            }
        })
    }

    fun fetchExpenseCategories()
    {
        ParameterService.getExpenseCategoryList().enqueue(object : Callback<Parameters>{
            override fun onResponse(call: Call<Parameters>, response: Response<Parameters>) {
                val data = response.body()
                if (data != null)
                {
                    val parameters : MutableList<Parameter> = mutableListOf()
                    for(parameter in data.data)
                    {
                         parameters.add(parameter)
                    }
                    _listExpenseCategory.postValue(parameters)
                }
            }

            override fun onFailure(call: Call<Parameters>, t: Throwable) {
                Log.e("ExpenseViewmodelMyExpense",t.message.toString())
            }

        })
    }

}