package com.works.crm_project.model


data class Parameters(
    val data: List<Parameter>,
    val success: Boolean,
    val message: Any? = null
)

data class Parameter(
    val id: Int,
    val description: String,
    val status: Boolean
)
