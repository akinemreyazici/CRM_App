package com.works.crm_project.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.works.crm_project.R
import com.works.crm_project.databinding.FragmentLoginBinding
import com.works.crm_project.manager.TokenManager
import com.works.crm_project.viewmodel.LoginViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private lateinit var viewModel: LoginViewModel

    private val binding get() = _binding!!
    private lateinit var controller: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            ).get(
                LoginViewModel::class.java
            )

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tokenManager = TokenManager(requireContext())
        tokenManager.clearData()
        controller = Navigation.findNavController(requireView())

        viewModel.setLoginListener(object : LoginViewModel.LoginListener {
            override fun onLoginSuccess(token: String,id : Int) {
                val email = binding.emailLoginEditText.text.toString()
                tokenManager.saveData(token,id,email)
                Log.e("Email",tokenManager.getEmail().toString())
                controller.navigate(R.id.action_loginFragment_to_nav_home)
            }

            override fun onLoginFailure(error: String) {
                val toast = Toast.makeText(requireContext(),error,Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()

            }
        })

        binding.loginButton.setOnClickListener {
            val email = binding.emailLoginEditText.text.toString()
            val password = binding.passwordLoginEditText.text.toString()
            viewModel.LoginAsEmployee(email, password)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

    }

}