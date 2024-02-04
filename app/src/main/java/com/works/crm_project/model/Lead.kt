package com.works.crm_project.model

data class Leads(
    val data : List<Lead>,
    val success : Boolean,
    val message : Any? = null
)

data class Lead(
    val id: Int?,
    val customerName: String,
    val currencyTypeId: Int,
    val leadTypeId: Int,
    val leadAmount: Double,
    val createDate: String,
    val resource: String,
    val precedenceId: Int,
    val title: String,
    val description: String,
    val userEmail : String,
    val leadStatusId: Int,
    val status: Boolean,
    val associatedOfferName: String? = null,
    val leadStatus : Parameter?,
    val leadType : Parameter?,
    val currencyType : Parameter?,
    val precedence : Parameter?
)


data class LeadAdd(
    val id: Int?,
    val customerName: String,
    val currencyTypeId: Int,
    val leadTypeId: Int,
    val leadAmount: Double,
    val createDate: String,
    val resource: String,
    val precedenceId: Int,
    val title: String,
    val description: String,
    val userEmail : String,
    val leadStatusId: Int,
    val status: Boolean,
    val associatedOfferName: String?
)

data class LeadById(
    val data : Lead,
    val success : Boolean,
    val message : Any? = null
)

