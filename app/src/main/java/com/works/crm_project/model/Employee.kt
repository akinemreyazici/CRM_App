package com.works.crm_project.model

data class EmployeeInfo (
    val data: List<Employee>,
    val success: Boolean,
    val message: String
)

data class EmployeeInfoById (
    val data: Employee,
    val success: Boolean,
    val message: String
)

data class Employee (
    val id: Int?,
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val tckn: String,
    val adress: String,
    val imageUrl: String,
    val workTitle : String
)

