package com.works.crm_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.works.crm_project.R
import com.works.crm_project.model.Expense


class customExpenseListAdapter(
    context: Context,
    resource: Int,
    private val list: List<Expense>
) : ArrayAdapter<Expense>(context, resource, list) {
    private var ListView: ListView? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val root = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_expense_list,parent,false)

        val expenseCategoryText = root.findViewById<TextView>(R.id.r_ExpenseCategory)
        val expenseReceiptDateText = root.findViewById<TextView>(R.id.r_ExpenseReceiptDate)
        val expenseAmountText = root.findViewById<TextView>(R.id.r_ExpenseAmount)



        val expense = list.get(position)
        expenseCategoryText.text = expense.expenseCategory.description
        expenseCategoryText.setTextColor(selectColourForCategory(expense.expenseCategory.id))
        expenseReceiptDateText.text = expense.receiptDate
        expenseAmountText.text = expense.amount.toString()



        return root
    }

    fun makeListView(listView: ListView) {
        ListView = listView
        notifyDataSetChanged()
    }

    fun selectColourForCategory(id : Int) : Int
    {
        return when(id){
            1-> ContextCompat.getColor(context, R.color.green)
            2-> ContextCompat.getColor(context, R.color.orange)
            3-> ContextCompat.getColor(context, R.color.red)
            4-> ContextCompat.getColor(context, R.color.blue)
            5-> ContextCompat.getColor(context, R.color.yellow)
            6-> ContextCompat.getColor(context, R.color.pink)
            else -> ContextCompat.getColor(context, R.color.black)

        }
    }


}
