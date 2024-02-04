package com.works.crm_project.adapter


import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.works.crm_project.R
import com.works.crm_project.model.Employee

class customEmployeeListAdapter(
    private val fragment: Fragment,
    private val list: List<Employee>
) : ArrayAdapter<Employee>(fragment.requireContext(), R.layout.custom_employee_list, list) {
    private var ListView: ListView? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val root = fragment.layoutInflater.inflate(R.layout.custom_employee_list, null, true)

        val employeeNameText = root.findViewById<TextView>(R.id.r_employeeNameTextView)
        val employeeTitleText = root.findViewById<TextView>(R.id.r_employeeTitleTextView)


        val employee = list.get(position)
        employeeNameText.text = employee.name + " " + employee.surname
        employeeTitleText.text = employee.workTitle


        return root
    }

    fun makeListView(listView: ListView) {
        ListView = listView
        notifyDataSetChanged()
    }
}