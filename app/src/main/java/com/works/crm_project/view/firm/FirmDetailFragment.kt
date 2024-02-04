package com.works.crm_project.view.firm

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.works.crm_project.R
import com.works.crm_project.common.CommonUtil
import com.works.crm_project.databinding.FragmentFirmDetailBinding
import com.works.crm_project.model.Firm
import com.works.crm_project.model.Parameter
import com.works.crm_project.viewmodel.FirmDetailViewModel
import java.util.Calendar

class FirmDetailFragment : Fragment() {

    private var _binding: FragmentFirmDetailBinding? = null
    private var id: Int = 0

    private val binding get() = _binding!!

    private lateinit var viewModel: FirmDetailViewModel

    private var selectedBusinessTypeId: Int? = null
    private var selectedCityTypeId: Int? = null
    private var selectedFirmAreaActivity: Int? = null

    private val cityDescriptionList = mutableListOf<String>()
    private val businessTypeDescriptionList = mutableListOf<String>()
    private val firmAreaDescriptionList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[FirmDetailViewModel::class.java]

        _binding = FragmentFirmDetailBinding.inflate(inflater, container, false)
        DefaultState()
        binding.firmDetailLayout.visibility = View.GONE
        binding.overlayFirmDetailLayout.visibility = View.VISIBLE
        Thread {
            viewModel.fetchBusinessTypeList()
            viewModel.fetchCityList()
            viewModel.fetchFirmAreaActivityList()
        }.start()

        val firmId = arguments?.getInt("FirmId", 0)
        if (firmId != null) {
            id = firmId
            Log.e("FİRMA", id.toString())
        }



        if (id != null && id != 0) {

            Handler().postDelayed({
                binding.firmDetailLayout.visibility = View.GONE
                binding.overlayFirmDetailLayout.visibility = View.VISIBLE
                Thread {
                    viewModel.getFirmById(id)
                    // Veriler yüklendikten sonra:
                    requireActivity().runOnUiThread {
                        viewModel.firm.observe(viewLifecycleOwner) { firm ->
                            firm?.let {
                                Log.e("FİRMAA", firm.toString())

                                DefaultState()
                                RestoreData(firm)
                                // Veriler yüklendikten sonra employeDetailLayout ve overlayLayout'u gizle
                                binding.firmDetailLayout.visibility = View.VISIBLE
                                binding.overlayFirmDetailLayout.visibility = View.GONE
                            }
                        }

                    }
                }.start()
            }, 350)
        }



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val businessTypeSpinner = binding.firmDetailBusinessTypeSpinner
        val citySpinner = binding.firmDetailCitySpinner
        val firmAreaActivitySpinner = binding.firmDetailActivityAreaSpinner

        viewModel.listBusinessType.observe(viewLifecycleOwner)
        { parameters ->
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

        viewModel.listCity.observe(viewLifecycleOwner)
        { parameters ->
            cityDescriptionList.clear()
            cityDescriptionList.addAll(parameters.map { it.description })

            val cityTypeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                cityDescriptionList
            )
            citySpinner.adapter = cityTypeAdapter
        }

        viewModel.listFirmAreaActivity.observe(viewLifecycleOwner)
        { parameters ->
            firmAreaDescriptionList.clear()
            firmAreaDescriptionList.addAll(parameters.map { it.description })

            val firmAreaActivityAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                firmAreaDescriptionList
            )
            firmAreaActivitySpinner.adapter = firmAreaActivityAdapter
        }

        businessTypeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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

        citySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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


        binding.firmDetailFoundingDateImageView.setOnClickListener {
            setDateWithDateDialog()

        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editMenu -> {
                Log.e("DENEME", "DÜZENLENİYOR")
                UpdateState()

                binding.firmUpdateCancelButton.setOnClickListener {
                    DefaultState()
                    viewModel.firm.observe(viewLifecycleOwner) {
                        RestoreData(it!!)
                    }
                }

                binding.firmUpdateConfirmButton.setOnClickListener {
                    CommonUtil.showAlertDialog(
                        requireContext(),
                        "Çalışanı Düzenle",
                        "Çalışanı düzenlemek istediğinizden emin misiniz?",
                        "Evet",
                        "Hayır",
                        {
                            // Evet düğmesine tıklandığında yapılacak işlemler
                            Log.e("GEL", getWrittenFirm().toString())
                            viewModel.updateFirm(getWrittenFirm())
                            findNavController().popBackStack()
                        },
                        {
                            // Hayır düğmesine tıklandığında yapılacak işlemler
                            // Kullanıcı işlemi iptal ettiği için bir şey yapmaya gerek yok.
                        }
                    )
                }
            }

            R.id.deleteMenu -> {
                CommonUtil.showAlertDialog(
                    requireContext(),
                    "Firma Sil",
                    "Bu firmayı silmek istediğinizden emin misiniz?",
                    "Evet",
                    "Hayır",
                    {
                        Log.e("DENEMEFirm", "ÇALIŞIYOR")
                        viewModel.deleteFirm(id)

                        // Silme işlemi tamamlandıktan sonra bir adım geriye git
                        findNavController().popBackStack()
                    },
                    {
                        // Kullanıcı işlemi iptal ettiği için bir şey yapmaya gerek yok.
                    }
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSelectedIdFromDescription(
        description: String,
        parameterList: List<Parameter>?
    ): Int? {

        val selectedItem = parameterList?.firstOrNull { it.description == description }
        return selectedItem?.id
    }

    fun setDateWithDateDialog() {
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

                binding.firmDetailFoundingDateTextView.text =
                    "$selectedYear-$ay-$selectedDayOfMonth"
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
        val name = binding.firmDetailNameEditText.text.toString()
        val email = binding.firmDetailEmailEditText.text.toString()
        val phone = binding.firmDetailPhoneEditText.text.toString()
        val website = binding.firmDetailWebsiteEditText.text.toString()
        val taxNumber = binding.firmDetailTaxNumberEditText.text.toString()
        val taxOffice = binding.firmDetailTaxOfficeEditText.text.toString()
        val foundingDate = binding.firmDetailFoundingDateTextView.text.toString()
        val mapLocation = binding.firmDetailMapLocationEditText.text.toString()
        val adress = binding.firmDetailAdressEditText.text.toString()
        val district = binding.firmDetailDistrictEditText.text.toString()
        val postcode = binding.firmDetailPostCodeEditText.text.toString()
        val linkedIn = binding.firmDetailLinkedInEditText.text.toString()
        val instagram = binding.firmDetailInstagramEditText.text.toString()
        val numberOfEmployee = binding.firmDetailEmployeeNumberEditText.text.toString().toInt()
        val description = binding.firmDetailDescriptionEditText.text.toString()
        val status = binding.firmDetailStatusSwitch.isChecked
        val isCustomer = binding.firmDetailCustomerCheckBox.isChecked
        val isSupplier = binding.firmDetailSupplierCheckBox.isChecked

        val firm = Firm(
            id,
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

    fun UpdateState() {
        binding.firmDetailNameEditText.isEnabled = true
        binding.firmDetailEmailEditText.isEnabled = true
        binding.firmDetailPhoneEditText.isEnabled = true
        binding.firmDetailWebsiteEditText.isEnabled = true
        binding.firmDetailWebsiteEditText.isEnabled = true
        binding.firmDetailTaxNumberEditText.isEnabled = true
        binding.firmDetailTaxOfficeEditText.isEnabled = true
        binding.firmDetailActivityAreaSpinner.isEnabled = true

        binding.firmDetailFoundingDateImageView.visibility = View.VISIBLE
        binding.firmDetailFoundingDateImageView.isEnabled = true

        binding.firmDetailBusinessTypeSpinner.isEnabled = true
        binding.firmDetailEmployeeNumberEditText.isEnabled = true
        binding.firmDetailCitySpinner.isEnabled = true
        binding.firmDetailDistrictEditText.isEnabled = true
        binding.firmDetailPostCodeEditText.isEnabled = true
        binding.firmDetailAdressEditText.isEnabled = true

        binding.firmDetailCustomerCheckBox.isEnabled = true
        binding.firmDetailSupplierCheckBox.isEnabled = true

        binding.firmDetailMapLocationEditText.isEnabled = true

        binding.firmDetailDescriptionEditText.isEnabled = true
        binding.firmDetailLinkedInEditText.isEnabled = true
        binding.firmDetailInstagramEditText.isEnabled = true
        binding.firmDetailStatusSwitch.isEnabled = true

        binding.firmUpdateConfirmButton.visibility = View.VISIBLE
        binding.firmUpdateCancelButton.visibility = View.VISIBLE
        binding.firmUpdateConfirmButton.isEnabled = true
        binding.firmUpdateCancelButton.isEnabled = true
    }

    fun DefaultState() {
        binding.firmDetailNameEditText.isEnabled = false
        binding.firmDetailEmailEditText.isEnabled = false
        binding.firmDetailPhoneEditText.isEnabled = false
        binding.firmDetailWebsiteEditText.isEnabled = false
        binding.firmDetailWebsiteEditText.isEnabled = false
        binding.firmDetailTaxNumberEditText.isEnabled = false
        binding.firmDetailTaxOfficeEditText.isEnabled = false
        binding.firmDetailActivityAreaSpinner.isEnabled = false

        binding.firmDetailFoundingDateImageView.visibility = View.INVISIBLE
        binding.firmDetailFoundingDateImageView.isEnabled = false

        binding.firmDetailBusinessTypeSpinner.isEnabled = false
        binding.firmDetailEmployeeNumberEditText.isEnabled = false
        binding.firmDetailCitySpinner.isEnabled = false
        binding.firmDetailDistrictEditText.isEnabled = false
        binding.firmDetailPostCodeEditText.isEnabled = false
        binding.firmDetailAdressEditText.isEnabled = false

        binding.firmDetailCustomerCheckBox.isEnabled = false
        binding.firmDetailSupplierCheckBox.isEnabled = false

        binding.firmDetailMapLocationEditText.isEnabled = false

        binding.firmDetailDescriptionEditText.isEnabled = false
        binding.firmDetailLinkedInEditText.isEnabled = false
        binding.firmDetailInstagramEditText.isEnabled = false
        binding.firmDetailStatusSwitch.isEnabled = false

        binding.firmUpdateConfirmButton.visibility = View.INVISIBLE
        binding.firmUpdateCancelButton.visibility = View.INVISIBLE
        binding.firmUpdateConfirmButton.isEnabled = false
        binding.firmUpdateCancelButton.isEnabled = false
    }

    fun RestoreData(firm: Firm?) {

        binding.firmDetailNameEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.name ?: "")

        binding.firmDetailEmailEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.email ?: "")

        binding.firmDetailPhoneEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.phone ?: "")

        binding.firmDetailWebsiteEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.webSite ?: "")

        binding.firmDetailTaxNumberEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.taxNumber ?: "")

        binding.firmDetailTaxOfficeEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.taxOffice ?: "")



        fun findIndexForActivityArea(): Int {
            val firmAreaActivityList = viewModel.listFirmAreaActivity.value
            if (firmAreaActivityList != null && firm?.activityArea != null) {
                for (index in firmAreaActivityList.indices) {
                    val item = firmAreaActivityList[index]
                    if (item.id == firm.activityArea.id.toInt()) {
                        return index
                    }
                }
            }
            return -1
        }

        binding.firmDetailActivityAreaSpinner.setSelection(findIndexForActivityArea())
        binding.firmDetailFoundingDateTextView.text = firm?.foundingDate

        fun findIndexForCity(): Int {
            val cityList = viewModel.listBusinessType.value
            if (cityList != null && firm?.city != null) {
                for (index in cityList.indices) {
                    val item = cityList[index]
                    if (item.id == firm.city.id.toInt()) {
                        return index
                    }
                }
            }
            return -1
        }

        binding.firmDetailCitySpinner.setSelection(findIndexForCity())

        fun findIndexForBusinessType(): Int {
            val businessTypeList = viewModel.listBusinessType.value
            if (businessTypeList != null && firm?.businessType != null) {
                for (index in businessTypeList.indices) {
                    val item = businessTypeList[index]
                    if (item.id == firm.businessType.id.toInt()) {
                        return index
                    }
                }
            }
            return -1
        }

        binding.firmDetailBusinessTypeSpinner.setSelection(findIndexForBusinessType())



        binding.firmDetailEmployeeNumberEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.numberOfEmployees?.toString() ?: "")

        binding.firmDetailDistrictEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.district ?: "")

        binding.firmDetailPostCodeEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.postCode ?: "")

        binding.firmDetailAdressEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.adress ?: "")


        if (firm != null) {
            binding.firmDetailCustomerCheckBox.isChecked = firm.isCustomer
            binding.firmDetailSupplierCheckBox.isChecked = firm.isSupplier
            binding.firmDetailStatusSwitch.isChecked = firm.status
        }


        binding.firmDetailMapLocationEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.mapLocation ?: "")

        binding.firmDetailDescriptionEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.description ?: "")

        binding.firmDetailLinkedInEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.linkedin ?: "")

        binding.firmDetailInstagramEditText.text =
            Editable.Factory.getInstance().newEditable(firm?.instagram ?: "")


    }


}