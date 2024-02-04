package com.works.crm_project.view.employee

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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.works.crm_project.R
import com.works.crm_project.common.CommonUtil
import com.works.crm_project.databinding.FragmentEmployeeDetailBinding
import com.works.crm_project.model.Employee
import com.works.crm_project.viewmodel.EmployeeDetailViewModel

class EmployeeDetailFragment : Fragment() {

    private var _binding: FragmentEmployeeDetailBinding? = null
    private lateinit var viewModel: EmployeeDetailViewModel
    private var id: Int = 0

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            ).get(
                EmployeeDetailViewModel::class.java
            )

        _binding = FragmentEmployeeDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val employeeId = arguments?.getInt("EmployeeId", 0)
        if (employeeId != null) {
            id = employeeId
        }
        Log.e("BEYZA", id.toString())

        if (id != 0 && id != null) {
            // employeDetailLayout ve overlayLayout'u görünür yap
            binding.employeeDetailLayout.visibility = View.GONE
            binding.overlayEmployeeDetailLayout.visibility = View.VISIBLE

            Handler().postDelayed({

                Thread {
                    viewModel.getEmployeeById(id)
                    // Veriler yüklendikten sonra:
                    requireActivity().runOnUiThread {
                        viewModel.employee.observe(viewLifecycleOwner) { employeeData ->
                            employeeData?.let {


                                DefaultState()
                                RestoreData(it)
                                // Veriler yüklendikten sonra employeDetailLayout ve overlayLayout'u gizle
                                binding.employeeDetailLayout.visibility = View.VISIBLE
                                binding.overlayEmployeeDetailLayout.visibility = View.GONE
                            }
                        }
                    }
                }.start()
            }, 350)
        }

        return root
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


                binding.employeeDetailUpdateCancelButton.setOnClickListener {
                    DefaultState()
                    viewModel.employee.observe(viewLifecycleOwner) {
                        RestoreData(it)
                    }
                }

                binding.employeeDetailUpdateConfirmButton.setOnClickListener {
                    CommonUtil.showAlertDialog(
                        requireContext(),
                        "Çalışanı Düzenle",
                        "Çalışanı düzenlemek istediğinizden emin misiniz?",
                        "Evet",
                        "Hayır",
                        {
                            // Evet düğmesine tıklandığında yapılacak işlemler
                            Log.e("GEL", getWrittenEmployee().toString())
                            viewModel.setEmployeeById(getWrittenEmployee())
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
                    "Çalışanı Sil",
                    "Bu çalışanı silmek istediğinizden emin misiniz?",
                    "Evet",
                    "Hayır",
                    {
                        Log.e("DENEME", "ÇALIŞIYOR")
                        viewModel.deleteEmployeeById(id)

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

    fun UpdateState() {
        binding.employeeDetailNameEditText.isEnabled = true
        binding.employeeDetailSurnameEditText.isEnabled = true
        binding.employeeDetailEmailEditText.isEnabled = true
        binding.employeeDetailPhoneEditText.isEnabled = true
        binding.employeeDetailTcknEditText.isEnabled = true
        binding.employeeDetailAddressEditText.isEnabled = true
        binding.employeeDetailWorkTitleEditText.isEnabled = true

        //binding.employeeProfileImageView.isEnabled = true

        binding.employeeDetailUpdateConfirmButton.visibility = View.VISIBLE
        binding.employeeDetailUpdateCancelButton.visibility = View.VISIBLE
        binding.employeeDetailUpdateConfirmButton.isEnabled = true
        binding.employeeDetailUpdateCancelButton.isEnabled = true
    }

    fun DefaultState() {
        binding.employeeDetailNameEditText.isEnabled = false
        binding.employeeDetailSurnameEditText.isEnabled = false
        binding.employeeDetailEmailEditText.isEnabled = false
        binding.employeeDetailPhoneEditText.isEnabled = false
        binding.employeeDetailTcknEditText.isEnabled = false
        binding.employeeDetailAddressEditText.isEnabled = false
        binding.employeeDetailWorkTitleEditText.isEnabled = false

        //binding.employeeProfileImageView.isEnabled = false

        binding.employeeDetailUpdateConfirmButton.visibility = View.INVISIBLE
        binding.employeeDetailUpdateCancelButton.visibility = View.INVISIBLE
        binding.employeeDetailUpdateConfirmButton.isEnabled = false
        binding.employeeDetailUpdateCancelButton.isEnabled = false
    }

    fun RestoreData(employeeData: Employee?) {
        binding.employeeDetailNameEditText.text =
            Editable.Factory.getInstance().newEditable(employeeData?.name ?: "")
        binding.employeeDetailSurnameEditText.text =
            Editable.Factory.getInstance().newEditable(employeeData?.surname ?: "")
        binding.employeeDetailEmailEditText.text =
            Editable.Factory.getInstance().newEditable(employeeData?.email ?: "")
        binding.employeeDetailPhoneEditText.text =
            Editable.Factory.getInstance().newEditable(employeeData?.phone ?: "")
        binding.employeeDetailTcknEditText.text =
            Editable.Factory.getInstance().newEditable(employeeData?.tckn ?: "")
        binding.employeeDetailAddressEditText.text =
            Editable.Factory.getInstance().newEditable(employeeData?.adress ?: "")
        binding.employeeDetailWorkTitleEditText.text = Editable.Factory.getInstance().newEditable(employeeData?.workTitle ?: "")
        //Glide.with(this).load(employeeData?.imageUrl).into(binding.employeeProfileImageView)
    }

    fun getWrittenEmployee() : Employee {
        val name = binding.employeeDetailNameEditText.text.toString()
        val surname = binding.employeeDetailSurnameEditText.text.toString()
        val email = binding.employeeDetailEmailEditText.text.toString()
        val phone = binding.employeeDetailPhoneEditText.text.toString()
        val tckn = binding.employeeDetailTcknEditText.text.toString()
        val adress = binding.employeeDetailAddressEditText.text.toString()
        val workTitle = binding.employeeDetailWorkTitleEditText.text.toString()
        // Görsel ekleme kısımları yapılacak
        return Employee(id,name,surname,email,phone,tckn,adress,"boş",workTitle)
    }



}