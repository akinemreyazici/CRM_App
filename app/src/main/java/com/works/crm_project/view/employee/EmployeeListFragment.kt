package com.works.crm_project.view.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.works.crm_project.R
import com.works.crm_project.adapter.customEmployeeListAdapter
import com.works.crm_project.databinding.FragmentEmployeeListBinding
import com.works.crm_project.manager.TokenManager
import com.works.crm_project.model.Employee
import com.works.crm_project.viewmodel.EmployeeListViewModel

class EmployeeListFragment : Fragment() {

    private var _binding: FragmentEmployeeListBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var controller: NavController
    private var viewModel: EmployeeListViewModel? = null
    private var adapter: customEmployeeListAdapter? = null
    private var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(EmployeeListViewModel::class.java)

        _binding = FragmentEmployeeListBinding.inflate(inflater, container, false)
        val root: View = binding.root


        listView = binding.listViewEmployee

        Thread{
            viewModel?.fetchData()
        }.start()

        val tokenManager = TokenManager(requireContext())
        val token = tokenManager.getToken()
        viewModel?.setAccessToken(token)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        controller = Navigation.findNavController(view)

        var tokenManager = TokenManager(requireContext())

        viewModel?.list?.observe(viewLifecycleOwner) {

            adapter = customEmployeeListAdapter(this, it)
            adapter?.makeListView(listView!!)
            listView?.adapter = adapter

        }

        binding.listViewEmployee.setOnItemClickListener { adapterView, view, i, l ->
            val selectedEmployee = listView?.getItemAtPosition(i) as Employee
            val bundle = Bundle()
            bundle.putInt("EmployeeId", selectedEmployee.id!!)

            val employeeDetailFragment = EmployeeDetailFragment()
            employeeDetailFragment.arguments = bundle

            controller.navigate(R.id.action_nav_home_to_employeeDetailFragment, bundle)
        }



        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenu -> {
                controller.navigate(R.id.action_nav_home_to_employeeAddFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        viewModel?.fetchData()
        adapter?.makeListView(listView!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}