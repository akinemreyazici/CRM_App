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
import com.works.crm_project.model.Firm


class customFirmListAdapter(
    context: Context,
    resource: Int,
    private val list: List<Firm>
) : ArrayAdapter<Firm>(context, resource, list) {
    private var ListView: ListView? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val root = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_firm_list,parent,false)

        val firmNameText = root.findViewById<TextView>(R.id.r_firmNameTextView)
        val firmAreaActivityText = root.findViewById<TextView>(R.id.r_firmActivityAreaTextView)
        val firmBusinessTypeText = root.findViewById<TextView>(R.id.r_FirmBusinessTypeTextView)
        val firmCityText = root.findViewById<TextView>(R.id.r_FirmCityTextView)


        val firm = list.get(position)
        firmNameText.text = firm.name

        firmAreaActivityText.text = firm.activityArea?.description
        firmAreaActivityText.setTextColor(selectColourFromId(firm.activityAreaId!!))

        firmBusinessTypeText.text = firm.businessType?.description
        firmBusinessTypeText.setTextColor(selectColourFromId(firm.businessTypeId!!))

        firmCityText.text = firm.city?.description



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
            2 -> ContextCompat.getColor(context, R.color.orange)
            3 -> ContextCompat.getColor(context, R.color.red)
            4 -> ContextCompat.getColor(context, R.color.blue)
            else -> ContextCompat.getColor(context, R.color.black)

        }
    }

}



