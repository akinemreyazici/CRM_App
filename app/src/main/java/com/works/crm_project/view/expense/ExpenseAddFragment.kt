package com.works.crm_project.view.expense

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedDispatcher
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.works.crm_project.R
import com.works.crm_project.common.CommonUtil
import com.works.crm_project.databinding.FragmentExpenseAddBinding
import com.works.crm_project.databinding.FragmentLeadAddBinding
import com.works.crm_project.manager.TokenManager
import com.works.crm_project.model.ExpenseAdd
import com.works.crm_project.model.Parameter
import com.works.crm_project.viewmodel.ExpenseAddViewModel
import com.works.crm_project.viewmodel.LeadAddViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ExpenseAddFragment : Fragment() {

    private var _binding: FragmentExpenseAddBinding? = null


    private val binding get() = _binding!!

    private lateinit var viewModel: ExpenseAddViewModel

    private var selectedExpenseCategoryId: Int? = null
    private var controller : NavController? = null

    private val expenseCategoryList = mutableListOf<String>()
    var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ExpenseAddViewModel::class.java)
        _binding = FragmentExpenseAddBinding.inflate(inflater, container, false)

        Thread {
            viewModel.fetchExpenseCategory()
        }.start()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        controller = Navigation.findNavController(view)
        val tokenManager = TokenManager(requireContext())
        //userId = tokenManager.getId()
        userId = tokenManager.getId()

        val expenseCategorySpinner = binding.expenseddExpenseCategorySpinner

        var currentDate = getCurrentDate()
        binding.expenseAddCreateDateTextview.text = currentDate

        viewModel.listExpenseCategory.observe(viewLifecycleOwner) { parameters ->
            // descriptionList'i temizle ve sadece description'ları ekle
            expenseCategoryList.clear()
            expenseCategoryList.addAll(parameters.map { it.description })

            // Spinner'a sadece description'ları ekle
            val businessTypeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                expenseCategoryList
            )
            expenseCategorySpinner.adapter = businessTypeAdapter
        }

        expenseCategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Seçilen öğenin id değerini al
                    selectedExpenseCategoryId = getSelectedIdFromDescription(
                        expenseCategorySpinner.selectedItem.toString(),
                        viewModel.listExpenseCategory.value
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Bir şey seçilmediğinde yapılacak işlemleri belirtin
                }
            }

        binding.expenseAddKDVRateEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateKdvAmount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.expenseAddExpenseReceiptDateImageView.setOnClickListener {
            setDateWithDateDialog()
        }

        binding.expenseAddButton.setOnClickListener {
            val expense = getWrittenExpense()
            if (expense?.KdvAmount == 0.0) {
                CommonUtil.showAlertDialog(
                    requireContext(),
                    "Eksik Doldurma",
                    "KDV değerinizi hesaplayamadık lütfen KDV değerini giriniz",
                    "Tamam",
                    "",
                    {

                    },
                    {
                        // Kullanıcı işlemi iptal ettiği için bir şey yapmaya gerek yok.
                    }
                )
            } else {
                if (expense != null) {
                    CommonUtil.showAlertDialog(
                        requireContext(),
                        "Masraf Ekle",
                        "Bu masrafı eklemek istediğinizden emin misiniz?",
                        "Evet",
                        "Hayır",
                        {
                            Log.e("DENEMELead", "ÇALIŞIYOR")
                            viewModel.addExpense(expense)
                            controller?.navigate(R.id.action_nav_expenseAdd_to_nav_expense)
                        },
                        {
                            // Kullanıcı işlemi iptal ettiği için bir şey yapmaya gerek yok.
                        }
                    )
                }
                else
                {
                    CommonUtil.showAlertDialog(
                        requireContext(),
                        "Eksik Doldurma",
                        "Doldurmadığınız bilgiler var, kontrol ediniz",
                        "Tamam",
                        "",
                        {

                        },
                        {
                            // Kullanıcı işlemi iptal ettiği için bir şey yapmaya gerek yok.
                        }
                    )
                }
            }

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

    fun calculateKdvAmount() {
        val amountText = binding.expenseAddAmountEditText.text.toString()
        val kdvRateText = binding.expenseAddKDVRateEditText.text.toString()

        if (amountText.isNotEmpty() && kdvRateText.isNotEmpty()) {
            val amount = amountText.toDouble()
            val kdvRate = kdvRateText.toDouble()

            val kdvAmount = if (amount > 0) {
                Math.round((amount * kdvRate / (100 + kdvRate)) * 100.0) / 100.0
            } else {
                0.0
            }

            // Sonucu TextView'e göster
            binding.expenseAddKDVAmountEditText.text = kdvAmount.toString()
        } else {
            // Amount veya KDV Rate boşsa veya geçersizse, sonuç 0 olarak gösterilebilir.
            binding.expenseAddKDVAmountEditText.text = "0.0"
        }
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

                binding.expenseAddExpenseReceiptDateTextView.text =
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

    fun getWrittenExpense(): ExpenseAdd? {
        if (binding.expenseAddAmountEditText.text.isNotEmpty()
            && binding.expenseAddKDVRateEditText.text.isNotEmpty()
            && binding.expenseAddExpenseReceiptDateTextView.text.isNotEmpty()
            && binding.expenseAddCreateDateTextview.text.isNotEmpty()
            && binding.expenseAddReceiptTaxNumberEditText.text.isNotEmpty()
            && binding.expenseAddReceiptNoEditText.text.isNotEmpty()
            && binding.expenseAddKDVAmountEditText.text.isNotEmpty()) {


            val employeeId = userId
            val receiptDate = binding.expenseAddExpenseReceiptDateTextView.text.toString()
            val createDate = binding.expenseAddCreateDateTextview.text.toString()
            val amount = binding.expenseAddAmountEditText.text.toString().toDouble()
            val kdvRate = binding.expenseAddKDVRateEditText.text.toString().toDouble()
            val imageUrl = "boş"
            val expenseCategoryId = selectedExpenseCategoryId!!
            val receiptTaxNumber =
                binding.expenseAddReceiptTaxNumberEditText.text.toString().toInt()
            val receiptNo = binding.expenseAddReceiptNoEditText.text.toString().toInt()
            val isConfirmed = false
            val kdvAmount = binding.expenseAddKDVAmountEditText.text.toString().toDouble()

            val expense = ExpenseAdd(
                employeeId,
                receiptDate,
                createDate,
                amount,
                kdvRate,
                imageUrl,
                expenseCategoryId,
                receiptTaxNumber,
                receiptNo,
                isConfirmed,
                kdvAmount
            )
            return expense
        } else return null

    }

}