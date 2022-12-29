package com.example.movieapp.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.data.locale.model.User
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.helper.TextMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    private var isEmailValid = false
    private var isPasswordValid = false

    private var user = User()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        val application = requireNotNull(this.activity).application
//        val factory = ViewModelFactory(application)
//
//        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            isAlreadyLogin().observe(viewLifecycleOwner) {
                if (it) {
                    val email = getEmail()
                    if (email.isNotEmpty()) {
                        val toHomeFragment = LoginFragmentDirections.actionLoginFragmentToMovieListFragment(email)
                        findNavController().navigate(toHomeFragment)
                    }
                }
            }
        }

        binding.apply {
            btnLogin.setOnClickListener {
                checkLogin()
                login()
            }

            tvRegister.setOnClickListener {
                val toRegisterFragment = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(toRegisterFragment)
            }
        }
    }

    private fun createUser() {
        binding.apply {
            user.apply {
                email = edtEmail.text.toString()
                password = edtPassword.text.toString()
            }
        }
    }

    private fun checkLogin() {
        binding.apply {
            viewModel.apply {

                checkEdtEmail(edtEmail.text.toString()).observe(viewLifecycleOwner) {
                    layoutEmail.isErrorEnabled = true
                    when (it) {
                        TextMessage.Ok -> {
                            isEmailValid = true
                            layoutEmail.isErrorEnabled = false
                            layoutEmail.error = null
                        }
                        TextMessage.TextEmpty -> layoutEmail.error = "Harap masukkan email"
                        TextMessage.EmailFormatInvalid -> layoutEmail.error = "Format email salah"
                        else -> Log.d("Reditya", it.toString())
                    }
                }

                checkEdtPassword(edtPassword.text.toString()).observe(viewLifecycleOwner) {
                    layoutPassword.isErrorEnabled = true
                    when (it) {
                        TextMessage.Ok -> {
                            isPasswordValid = true
                            layoutPassword.isErrorEnabled = false
                            layoutPassword.error = null
                        }
                        TextMessage.TextEmpty -> layoutPassword.error = "Harap masukkan password"
                        TextMessage.PasswordTooShort -> layoutPassword.error = "Passwod minimal 8 karakter"
                        else -> Log.d("Reditya", it.toString())
                    }
                }
            }
        }
    }

    private fun login() {
        createUser()
        viewModel.apply {
            login(user).observe(viewLifecycleOwner) {
                if (isEmailValid && isPasswordValid && it == TextMessage.Ok) {
                    val toHomeFragment = LoginFragmentDirections.actionLoginFragmentToMovieListFragment(user.email)
                    findNavController().navigate(toHomeFragment)
                } else if (it == TextMessage.WrongEmailOrPassword) {
                    Toast.makeText(
                        requireContext(),
                        "Email atau Password salah",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}