package com.works.crm_project.view.firm

import android.app.DatePickerDialog
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
import com.works.crm_project.databinding.FragmentFirmAddBinding
import com.works.crm_project.model.Firm
import com.works.crm_project.model.Parameter
import com.works.crm_project.viewmodel.FirmAddViewModel
import java.util.Calendar

class FirmAddFragment : Fragment() {

    private var _binding: FragmentFirmAddBinding? = null


    private val binding get() = _binding!!

    private lateinit var viewModel: FirmAddViewModel

    private var selectedBusinessTypeId: Int? = null
    private var selectedCityTypeId: Int? = null
    private var selectedFirmAreaActivity: Int? = null

    private val cityDescriptionList = mutableListOf<String>()
    private val businessTypeDescriptionList = mutableListOf<String>()
    private val firmAreaDescriptionList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FirmAddViewModel::class.java)

        _binding = FragmentFirmAddBinding.inflate(inflater, container, false)

        Thread {
            viewModel.fetchBusinessTypeList()
            viewModel.fetchCityList()
            viewModel.fetchFirmAreaActivityList()
        }.start()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val businessTypeSpinner = binding.firmBusinessTypeSpinner
        val citySpinner = binding.firmCitySpinner
        val firmAreaActivitySpinner = binding.firmActivityAreaSpinner

        viewModel.listBusinessType.observe(viewLifecycleOwner) { parameters ->
            // descriptionList'i temizle ve sadece description'ları ekle
            businessTypeDescriptionList.clear()
            businessTypeDescriptionList.addAll(parameters.map { it.description })

            // Spinner'a sadece description'ları ekle
            val businessTypeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                businessTypeDescriptionList
            )
            businessTypeSpinner.adapter = businessTypeAdapter
        }

        viewModel.listCity.observe(viewLifecycleOwner) { parameters ->
            cityDescriptionList.clear()
            cityDescriptionList.addAll(parameters.map { it.description })

            val cityTypeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                cityDescriptionList
            )
            citySpinner.adapter = cityTypeAdapter
        }

        viewModel.listFirmAreaActivity.observe(viewLifecycleOwner) { parameters ->
            firmAreaDescriptionList.clear()
            firmAreaDescriptionList.addAll(parameters.map { it.description })

            val firmAreaActivityAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                firmAreaDescriptionList
            )
            firmAreaActivitySpinner.adapter = firmAreaActivityAdapter
        }

        businessTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Seçilen öğenin id değerini al
                selectedBusinessTypeId = getSelectedIdFromDescription(
                    businessTypeSpinner.selectedItem.toString(),
                    viewModel.listBusinessType.value
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Bir şey seçilmediğinde yapılacak işlemleri belirtin
            }
        }

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedCityTypeId = getSelectedIdFromDescription(
                    citySpinner.selectedItem.toString(),
                    viewModel.listCity.value
                )
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        firmAreaActivitySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedFirmAreaActivity = getSelectedIdFromDescription(
                        firmAreaActivitySpinner.selectedItem.toString(),
                        viewModel.listFirmAreaActivity.value
                    )
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        binding.firmFoundingDateImageView.setOnClickListener {
            setDateWithDateDialog()

        }



        binding.firmAddButton.setOnClickListener {
            val firm = getWrittenFirm()
            viewModel.addFirm(firm)
            findNavController().popBackStack()
            Log.e("Firm", firm.toString())
        }



        super.onViewCreated(view, savedInstanceState)
    }

    private fun getSelectedIdFromDescription(
        description: String,
        parameterList: List<Parameter>?
    ): Int? {

        val selectedItem = parameterList?.firstOrNull { it.description == description }
        return selectedItem?.id
    }

    fun setDateWithDateDialog(){
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Seçilen tarihi kullan
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)

                // İşlemleriniz
                var ay = "${selectedMonth + 1}"
                if (selectedMonth + 1 < 10) {
                    ay = "0${selectedMonth + 1}"
                }

                binding.firmFoundingDateTextView.text = "$selectedYear-$ay-$selectedDayOfMonth"
            },
            year,
            month,
            dayOfMonth
        )

        // Minimum tarih olarak 1950 yılını belirle
        val minDate = Calendar.getInstance()
        minDate.set(1950, 0, 1)
        datePickerDialog.datePicker.minDate = minDate.timeInMillis

        // Maksimum tarih olarak bugünü belirle
        datePickerDialog.datePicker.maxDate = currentDate.timeInMillis

        datePickerDialog.show()

    }


    fun getWrittenFirm(): Firm {
        val name = binding.firmNameEditText.text.toString()
        val email = binding.firmEmailEditText.text.toString()
        val phone = binding.firmPhoneEditText.text.toString()
        val website = binding.firmWebsiteEditText.text.toString()
        val taxNumber = binding.firmTaxNumberEditText.text.toString()
        val taxOffice = binding.firmTaxOfficeEditText.text.toString()
        val foundingDate = binding.firmFoundingDateTextView.text.toString()
        val mapLocation = binding.firmMapLocationEditText.text.toString()
        val adress = binding.firmAdressEditText.text.toString()
        val district = binding.firmDistrictEditText.text.toString()
        val postcode = binding.firmPostCodeEditText.text.toString()
        val linkedIn = binding.firmLinkedInEditText.text.toString()
        val instagram = binding.firmInstagramEditText.text.toString()
        val numberOfEmployee = binding.firmEmployeeNumberEditText.text.toString().toInt()
        val description = binding.firmDescriptionEditText.text.toString()
        val status = binding.firmStatusSwitch.isChecked
        val isCustomer = binding.firmCustomerCheckBox.isChecked
        val isSupplier = binding.firmSupplierCheckBox.isChecked

        val firm = Firm(
            null,
            selectedFirmAreaActivity,
            selectedBusinessTypeId,
            selectedCityTypeId,
            name,
            mapLocation,
            adress,
            district,
            postcode,
            phone,
            email,
            website,
            taxNumber,
            taxOffice,
            foundingDate,
            linkedIn,
            instagram,
            numberOfEmployee,
            description,
            status,
            isCustomer,
            isSupplier,
            null,
            null,
            null
        )

        return firm
    }


}