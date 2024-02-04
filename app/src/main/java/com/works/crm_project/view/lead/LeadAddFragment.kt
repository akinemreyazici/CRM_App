package com.works.crm_project.view.lead

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.works.crm_project.common.CommonUtil
import com.works.crm_project.viewmodel.LeadAddViewModel
import com.works.crm_project.databinding.FragmentLeadAddBinding
import com.works.crm_project.manager.TokenManager
import com.works.crm_project.model.Lead
import com.works.crm_project.model.LeadAdd
import com.works.crm_project.model.Parameter
import java.text.SimpleDateFormat
import java.util.Date

class LeadAddFragment : Fragment() {

    private var _binding: FragmentLeadAddBinding? = null


    private val binding get() = _binding!!

    private lateinit var viewModel: LeadAddViewModel

    private var selectedLeadTypeId: Int? = null
    private var selectedLeadStatusId: Int? = null
    private var selectedCurrencyTypeId: Int? = null
    private var selectedPrecedenceId: Int? = null

    private val leadTypeList = mutableListOf<String>()
    private val leadStatusList = mutableListOf<String>()
    private val currencyTypeList = mutableListOf<String>()
    private val precedenceTypeList = mutableListOf<String>()

    //var userId: Int = 0
    var userEmail : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LeadAddViewModel::class.java)
        _binding = FragmentLeadAddBinding.inflate(inflater, container, false)

        Thread {
            viewModel.fetchLeadType()
            viewModel.fetchLeadStatus()
            viewModel.fetchCurrencyType()
            viewModel.fetchPrecedenceType()
        }.start()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tokenManager = TokenManager(requireContext())
        //userId = tokenManager.getId()
        val email = tokenManager.getEmail()
        if (email != null){
            userEmail = email
        }


        val leadTypeSpinner = binding.leadAddLeadTypeSpinner
        val leadStatusSpinner = binding.leadAddLeadStatusSpinner
        val currencyTypeSpinner = binding.leadAddCurrencyTypeSpinner
        val precedenceTypeSpinner = binding.leadAddPrecedenceSpinner

        var currentDate  = getCurrentDate()
       binding.leadAddCreateDateTextview.text = currentDate

        viewModel.listLeadTypes.observe(viewLifecycleOwner) { parameters ->
            // descriptionList'i temizle ve sadece description'ları ekle
            leadTypeList.clear()
            leadTypeList.addAll(parameters.map { it.description })

            // Spinner'a sadece description'ları ekle
            val businessTypeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                leadTypeList
            )
            leadTypeSpinner.adapter = businessTypeAdapter
        }

        viewModel.listLeadStatus.observe(viewLifecycleOwner) { parameters ->
            // descriptionList'i temizle ve sadece description'ları ekle
            leadStatusList.clear()
            leadStatusList.addAll(parameters.map { it.description })

            // Spinner'a sadece description'ları ekle
            val businessTypeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                leadStatusList
            )
            leadStatusSpinner.adapter = businessTypeAdapter
        }

        viewModel.listCurrencyType.observe(viewLifecycleOwner) { parameters ->
            // descriptionList'i temizle ve sadece description'ları ekle
            currencyTypeList.clear()
            currencyTypeList.addAll(parameters.map { it.description })

            // Spinner'a sadece description'ları ekle
            val businessTypeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                currencyTypeList
            )
            currencyTypeSpinner.adapter = businessTypeAdapter
        }

        viewModel.listPrecedenceType.observe(viewLifecycleOwner) { parameters ->
            // descriptionList'i temizle ve sadece description'ları ekle
            precedenceTypeList.clear()
            precedenceTypeList.addAll(parameters.map { it.description })

            // Spinner'a sadece description'ları ekle
            val businessTypeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                precedenceTypeList
            )
            precedenceTypeSpinner.adapter = businessTypeAdapter
        }

        leadTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Seçilen öğenin id değerini al
                selectedLeadTypeId = getSelectedIdFromDescription(
                    leadTypeSpinner.selectedItem.toString(),
                    viewModel.listLeadTypes.value
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Bir şey seçilmediğinde yapılacak işlemleri belirtin
            }
        }

        leadStatusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Seçilen öğenin id değerini al
                selectedLeadStatusId = getSelectedIdFromDescription(
                    leadStatusSpinner.selectedItem.toString(),
                    viewModel.listLeadStatus.value
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Bir şey seçilmediğinde yapılacak işlemleri belirtin
            }
        }

        currencyTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Seçilen öğenin id değerini al
                selectedCurrencyTypeId = getSelectedIdFromDescription(
                    currencyTypeSpinner.selectedItem.toString(),
                    viewModel.listCurrencyType.value
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Bir şey seçilmediğinde yapılacak işlemleri belirtin
            }
        }

        precedenceTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Seçilen öğenin id değerini al
                selectedPrecedenceId = getSelectedIdFromDescription(
                    precedenceTypeSpinner.selectedItem.toString(),
                    viewModel.listPrecedenceType.value
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Bir şey seçilmediğinde yapılacak işlemleri belirtin
            }
        }


        binding.leadAddButton.setOnClickListener {
            val lead = getWrittenLead()
            CommonUtil.showAlertDialog(
                requireContext(),
                "Duyum Ekle",
                "Bu duyumu eklemek istediğinizden emin misiniz?",
                "Evet",
                "Hayır",
                {
                    Log.e("DENEMELead", "ÇALIŞIYOR")
                    viewModel.addLead(lead)
                    findNavController().popBackStack()
                },
                {
                    // Kullanıcı işlemi iptal ettiği için bir şey yapmaya gerek yok.
                }
            )
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getCurrentDate(): String {
        // Şu anki tarihi almak için SimpleDateFormat kullan
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())

        return currentDate
    }


    private fun getSelectedIdFromDescription(
        description: String,
        parameterList: List<Parameter>?
    ): Int? {

        val selectedItem = parameterList?.firstOrNull { it.description == description }
        return selectedItem?.id
    }

    fun getWrittenLead() : LeadAdd {

        val customerName: String = binding.leadAddCustomerNameEditText.text.toString()
        val currencyTypeId: Int = selectedCurrencyTypeId!!
        val leadTypeId: Int = selectedLeadTypeId!!
        val leadAmount: Double = binding.leadAddLeadAmountEditText.text.toString().toDouble()
        val createDate: String = binding.leadAddCreateDateTextview.text.toString()
        val resource: String = binding.leadAddResourceEditText.text.toString()
        val precedenceId: Int = selectedPrecedenceId!!
        val title: String = binding.leadAddTitleEditText.text.toString()
        val description: String = binding.leadAddCreateDateTextview.text.toString()
        val userEmail : String = userEmail
        //val userId: Int = userId
        val leadStatusId: Int = selectedLeadStatusId!!
        val status: Boolean = false
        val associatedOfferName = "rastgele"

        val lead = LeadAdd(
            null,
            customerName,
            currencyTypeId,
            leadTypeId,
            leadAmount,
            createDate,
            resource,
            precedenceId,
            title,
            description,
            userEmail,
            leadStatusId,
            status,
            associatedOfferName
        )

        return lead
    }
}