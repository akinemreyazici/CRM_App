package com.works.crm_project.view.lead

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.works.crm_project.R
import com.works.crm_project.common.CommonUtil
import com.works.crm_project.databinding.FragmentFirmDetailBinding
import com.works.crm_project.databinding.FragmentLeadDetailBinding
import com.works.crm_project.model.Lead
import com.works.crm_project.model.LeadById
import com.works.crm_project.model.Parameter
import com.works.crm_project.viewmodel.FirmDetailViewModel
import com.works.crm_project.viewmodel.LeadDetailViewModel

class LeadDetailFragment : Fragment() {

    private var _binding: FragmentLeadDetailBinding? = null
    private var id: Int = 0

    private val binding get() = _binding!!

    private lateinit var viewModel: LeadDetailViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LeadDetailViewModel::class.java]

        _binding = FragmentLeadDetailBinding.inflate(inflater, container, false)

        Thread {
            viewModel.fetchLeadStatus()
            viewModel.fetchLeadType()
            viewModel.fetchCurrencyType()
            viewModel.fetchPrecedenceType()
        }.start()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.leadDetailLayout.visibility = View.GONE
        binding.overlayLeadDetailLayout.visibility = View.VISIBLE

        val leadId = arguments?.getInt("LeadId",0)
        if (leadId != null)
        {
            id = leadId
            Log.e("LEADID",id.toString())

        }


        if (id != null && id != 0)
        {
            Handler().postDelayed({
                binding.leadDetailLayout.visibility = View.GONE
                binding.overlayLeadDetailLayout.visibility = View.VISIBLE
                Thread {
                    viewModel.getLeadById(id)

                    requireActivity().runOnUiThread {
                        viewModel.lead.observe(viewLifecycleOwner) {

                            if (it != null) {
                                restoreData(it)
                                binding.leadDetailLayout.visibility = View.VISIBLE
                                binding.overlayLeadDetailLayout.visibility = View.GONE
                            }


                        }
                    }
                }.start()
            },350)
        }

        binding.leadDeleteButton.setOnClickListener {
            CommonUtil.showAlertDialog(
                requireContext(),
                "Duyum Sil",
                "Bu duyumu silmek istediğinizden emin misiniz?",
                "Evet",
                "Hayır",
                {
                    Log.e("DENEMELead", "ÇALIŞIYOR")
                    viewModel.deleteLead(id)

                    // Silme işlemi tamamlandıktan sonra bir adım geriye git
                    findNavController().popBackStack()
                },
                {
                    // Kullanıcı işlemi iptal ettiği için bir şey yapmaya gerek yok.
                }
            )

        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getSelectedDescriptionFromId(
        Id : Int,
        parameterList: List<Parameter>?
    ): String? {

        val selectedItem = parameterList?.firstOrNull { it.id == Id }
        return selectedItem?.description
    }

    fun restoreData(lead : LeadById){

        binding.leadDetailCustomerNameText.text =  lead.data.customerName
        binding.leadDetailLeadTypeText.text = getSelectedDescriptionFromId(lead.data.leadTypeId,viewModel.listLeadTypes.value)
        binding.leadDetailCreateDateTextview.text = lead.data.createDate
        binding.leadDetailResourceText.text =  lead.data.resource
        binding.leadDetailTitleText.text = lead.data.title
        binding.leadDetailDescriptionText.text = lead.data.description
        binding.leadDetailLeadAmountText.text = lead.data.leadAmount.toString()
        binding.leadDetailCurrencyTypeText.text = getSelectedDescriptionFromId(lead.data.currencyTypeId,viewModel.listCurrencyType.value)
        binding.leadDetailPrecedenceText.text = getSelectedDescriptionFromId(lead.data.precedenceId,viewModel.listPrecedenceType.value)
        binding.leadDetailLeadStatusText.text = getSelectedDescriptionFromId(lead.data.leadStatusId,viewModel.listLeadStatus.value)
    }
}