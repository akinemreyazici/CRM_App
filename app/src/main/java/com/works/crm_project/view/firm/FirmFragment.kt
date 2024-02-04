package com.works.crm_project.view.firm

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
import com.works.crm_project.adapter.customFirmListAdapter
import com.works.crm_project.databinding.FragmentFirmListBinding
import com.works.crm_project.model.Firm
import com.works.crm_project.view.employee.EmployeeDetailFragment
import com.works.crm_project.viewmodel.FirmViewModel

class FirmFragment : Fragment() {

    private var _binding: FragmentFirmListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var controller: NavController
    private var viewModel: FirmViewModel? = null
    private var adapter: customFirmListAdapter? = null
    private var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(FirmViewModel::class.java)

        _binding = FragmentFirmListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        controller = Navigation.findNavController(view)
        listView = binding.listViewFirm

        Thread {
            viewModel?.fetchData()
        }.start()

        viewModel?.list?.observe(viewLifecycleOwner) {

            adapter = customFirmListAdapter(requireContext(),R.layout.custom_employee_list,it)
            adapter?.makeListView(listView!!)
            listView?.adapter = adapter

        }

        binding.listViewFirm.setOnItemClickListener { adapterView, view, i, l ->
            val selectedFirm = listView?.getItemAtPosition(i) as Firm
            val bundle = Bundle()
            bundle.putInt("FirmId", selectedFirm.id!!)

            val employeeDetailFragment = EmployeeDetailFragment()
            employeeDetailFragment.arguments = bundle

            controller.navigate(R.id.action_nav_firm_to_firmDetailFragment, bundle)
        }

        binding.materialButtonToggleGroup.addOnButtonCheckedListener{toggleButtonGroup, checkedId, isChecked ->
            if (isChecked)
            {
                when(checkedId)
                {
                    R.id.btnShowSupplierList -> {
                        binding.materialButtonToggleGroup.check(R.id.btnShowSupplierList)
                        viewModel?.fetchSupplierData()
                        adapter?.makeListView(listView!!)
                    }
                    R.id.btnShowCustomerList -> {
                        binding.materialButtonToggleGroup.check(R.id.btnShowCustomerList)
                        viewModel?.fetchCustomerData()
                        adapter?.makeListView(listView!!)
                    }
                    R.id.btnShowAllList -> {
                        binding.materialButtonToggleGroup.check(R.id.btnShowAllList)
                        viewModel?.fetchData()
                        adapter?.makeListView(listView!!)
                    }
                }
            }

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
                controller.navigate(R.id.action_nav_firm_to_firmAddFragment)
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