package com.works.crm_project.view.employee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.works.crm_project.common.CommonUtil
import com.works.crm_project.databinding.FragmentEmployeeAddBinding
import com.works.crm_project.model.Employee
import com.works.crm_project.viewmodel.EmployeeAddViewModel

class EmployeeAddFragment : Fragment() {

    private var _binding: FragmentEmployeeAddBinding? = null
    private lateinit var viewModel: EmployeeAddViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            ).get(
                EmployeeAddViewModel::class.java
            )

        _binding = FragmentEmployeeAddBinding.inflate(inflater, container, false)

        binding.employeeAddButton.setOnClickListener {
            CommonUtil.showAlertDialog(
                requireContext(),
                "Çalışan Ekle",
                "Bu çalışanı eklemek istediğinize emin misiniz?",
                "Evet",
                "Hayır",
                {
                    // Pozitif kısım
                    viewModel.addEmployee(getWrittenEmployee())
                    findNavController().popBackStack()
                },
                {
                    // Negatif kısım
                }
            )
        }


        return binding.root
    }


    fun getWrittenEmployee(): Employee {
        val name = binding.employeeNameEditText.text.toString()
        val surname = binding.employeeSurnameEditText.text.toString()
        val email = binding.employeeEmailEditText.text.toString()
        val phone = binding.employeePhoneEditText.text.toString()
        val tckn = binding.employeeTcknEditText.text.toString()
        val adress = binding.employeeAddressEditText.text.toString()
        val workTitle = binding.employeeWorkTitleEditText.text.toString()
        // Görsel ekleme kısımları yapılacak
        return Employee(null,name, surname, email, phone, tckn, adress, "boş",workTitle)
    }

}