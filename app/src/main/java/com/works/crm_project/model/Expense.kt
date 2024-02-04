package com.works.crm_project.model

data class ExpenseList(
    val data: List<Expense>,
    val success: Boolean,
    val message: Any? = null
)

data class Expense (
    val id: Int,
    val userId: Int,
    val receiptDate: String,
    val createdDate: String,
    val amount: Double,
    val kdvRate: Double,
    //val imageUrl: String,
    val expenseCategoryId: Int,
    val receiptTaxNumber: Int,
    val receiptNo: Int,
    val kdvAmount: Double,
    val isConfirmed: Boolean,
    //val user: User,
    val expenseCategory: Parameter
)

data class ExpenseAdd(
    val EmployeId: Int,
    val ReceiptDate: String,
    val CreateDate: String,
    val Amount: Double,
    val KdvRate: Double,
    val ImageUrl: String? = null,
    val ExpenseCategoryId: Int,
    val ReceiptTaxNumber: Int,
    val ReceiptNo: Int,
    val isConfirmed: Boolean = false,
    val KdvAmount : Double // bunu expenseAdd de hesaplıyorum
)


