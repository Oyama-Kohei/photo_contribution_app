package com.example.android_trip_2023_app.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_trip_2023_app.databinding.FragmentLoginBinding
import com.example.android_trip_2023_app.activity.HomeActivity
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.view_model.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var emailField: TextInputLayout
    private lateinit var passwordField: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailField = this.requireView().findViewById(R.id.emailField)
        passwordField = this.requireView().findViewById(R.id.passwordField)

        viewModel.onTransit.observe(
            viewLifecycleOwner,
        ) {
            val intent = HomeActivity.getIntent(requireContext())
            startActivity(intent)
        }

        viewModel.errorDialogMsg.observe(
            viewLifecycleOwner,
        ) { msg ->
            if (msg.isNotEmpty()) {
                AlertDialog.Builder(context, R.style.AlertDialog)
                    .setMessage(msg)
                    .setPositiveButton("はい", null)
                    .show()
            }
        }

        viewModel.idErrorMsg.observe(
            viewLifecycleOwner,
        ) { msg ->
            emailField.error = msg
        }

        viewModel.passwordErrorMsg.observe(
            viewLifecycleOwner,
        ) { msg ->
            passwordField.error = msg
        }
    }
}
