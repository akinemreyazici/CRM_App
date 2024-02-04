package com.works.crm_project.view.lead

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ListView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.works.crm_project.R
import com.works.crm_project.adapter.customFirmListAdapter
import com.works.crm_project.viewmodel.LeadViewModel
import com.works.crm_project.adapter.customLeadListAdapter
import com.works.crm_project.databinding.FragmentFirmListBinding
import com.works.crm_project.databinding.FragmentLeadBinding
import com.works.crm_project.model.Lead
import com.works.crm_project.viewmodel.FirmViewModel

class LeadFragment : Fragment() {

    private var _binding: FragmentLeadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var controller: NavController
    private var viewModel: LeadViewModel? = null
    private var adapter: customLeadListAdapter? = null
    private var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LeadViewModel::class.java)

        _binding = FragmentLeadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        controller = Navigation.findNavController(view)
        listView = binding.listViewLead

        Thread {
            viewModel?.fetchData()
        }.start()

        viewModel?.list?.observe(viewLifecycleOwner) {
            adapter = customLeadListAdapter(requireContext(), R.layout.custom_employee_list,it)
            adapter?.makeListView(listView!!)
            listView?.adapter = adapter
        }

        binding.listViewLead.setOnItemClickListener { adapterView, view, i, l ->
            val selectedLead = binding.listViewLead.getItemAtPosition(i) as Lead
            val  bundle = Bundle()
            bundle.putInt("LeadId",selectedLead.id!!)



            val leadDetailFragment = LeadDetailFragment()
            leadDetailFragment.arguments = bundle

            controller.navigate(R.id.action_nav_lead_to_leadDetailFragment,bundle)
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
                controller.navigate(R.id.action_leadFragment_to_leadAddFragment)
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