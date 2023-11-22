package com.example.android_trip_2023_app.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LoginViewModel()::class.java]
        viewModel.init(requireContext())
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailField = this.requireView().findViewById(R.id.emailField)

        viewModel.onTransit.observe(
            viewLifecycleOwner,
        ) {
            if(it) {
                val intent = HomeActivity.getIntent(requireContext())
                startActivity(intent)
            }
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

        viewModel.onLoading.observe(
            viewLifecycleOwner,
        ) {
            if (it) {
                showLoading(view)
            } else {
                hideLoading(view)
            }
        }

        viewModel.idErrorMsg.observe(
            viewLifecycleOwner,
        ) { msg ->
            emailField.error = msg
        }
    }

    private fun showLoading(view: View) {
        val loadingView: View = view.findViewById(R.id.progress_circular_view)
        val loadingProgressBar: ProgressBar = view.findViewById(R.id.progress_circular)
        loadingView.visibility = View.VISIBLE
        loadingProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading(view: View) {
        val loadingView: View = view.findViewById(R.id.progress_circular_view)
        val loadingProgressBar: ProgressBar = view.findViewById(R.id.progress_circular)
        loadingView.visibility = View.GONE
        loadingProgressBar.visibility = View.GONE
    }
}
