package com.works.crm_project.model

data class Firms (
    val data: List<Firm>,
    val success: Boolean,
    val message: Any? = null
)

data class Firm (
    val id: Int?,
    val activityAreaId: Int?,
    val businessTypeId: Int?,
    val cityId: Int?,
    val name: String,
    val mapLocation: String,
    val adress: String,
    val district: String,
    val postCode: String,
    val phone: String,
    val email: String,
    val webSite: String,
    val taxNumber: String,
    val taxOffice: String?,
    val foundingDate: String,
    val linkedin: String,
    val instagram: String,
    val numberOfEmployees: Int,
    val description: String,
    val status: Boolean,
    val isCustomer: Boolean,
    val isSupplier: Boolean,
    val activityArea: ActivityArea?,
    val businessType: ActivityArea?,
    val city: ActivityArea?
)

data class FirmInfoById (
    val data: Firm,
    val success: Boolean,
    val message: String
)


data class ActivityArea (
    val id: Long,
    val description: String,
    val status: Boolean
)