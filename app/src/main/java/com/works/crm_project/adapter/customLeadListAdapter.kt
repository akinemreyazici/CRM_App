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
import com.works.crm_project.model.Lead


class customLeadListAdapter(
    context: Context,
    resource: Int,
    private val list: List<Lead>
) : ArrayAdapter<Lead>(context, resource, list) {
    private var ListView: ListView? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val root = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_lead_list,parent,false)

        val customerNameText = root.findViewById<TextView>(R.id.r_LeadCustomerNameTextView)
        val leadTypeText = root.findViewById<TextView>(R.id.r_LeadLeadTypeTextView)
        val precedenceTypeText = root.findViewById<TextView>(R.id.r_LeadPrecedenceTypeTextView)
        val leadStatusText = root.findViewById<TextView>(R.id.r_LeadLeadStatusTextView)
        val amountText = root.findViewById<TextView>(R.id.r_LeadAmountTextView)


        val lead = list.get(position)
        customerNameText.text = lead.customerName

        leadTypeText.text = lead.leadType?.description
        leadTypeText.setTextColor(selectColourFromId(lead.leadTypeId))

        precedenceTypeText.text = lead.precedence?.description
        precedenceTypeText.setTextColor(selectColourFromId(lead.precedenceId))

        leadStatusText.text = lead.leadStatus?.description
        leadStatusText.setTextColor(selectColourFromId(lead.leadStatusId))

        val currency = selectCurrencyFromId(lead.currencyTypeId)
        amountText.text = lead.leadAmount.toString() + " " + currency




        return root
    }

    fun makeListView(listView: ListView) {
        ListView = listView
        notifyDataSetChanged()
    }

    fun selectColourFromId(id : Int) : Int
    {

        return when(id){
            1 -> ContextCompat.getColor(context, R.color.green)
            2 -> ContextCompat.getColor(context, R.color.blue)
            3 -> ContextCompat.getColor(context, R.color.red)
            4 -> ContextCompat.getColor(context, R.color.orange)
            else -> ContextCompat.getColor(context, R.color.black)

        }
    }

    fun selectCurrencyFromId(id : Int) : String
    {

        return when(id){
            1 -> "Dolar"
            2 -> "Euro"
            else -> "TL"
        }
    }




}



