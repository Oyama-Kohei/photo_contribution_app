package com.example.android_trip_2023_app.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.adapter.ContributionListAdapter
import com.example.android_trip_2023_app.databinding.FragmentContributionBinding
import com.example.android_trip_2023_app.view_model.ContributionViewModel

class ContributionFragment : Fragment() {
    private lateinit var viewModel: ContributionViewModel
    private lateinit var binding: FragmentContributionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContributionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ContributionViewModel::class.java]

        viewModel.init(requireContext())
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.contribution_list)

        viewModel.contributionData.observe(
            viewLifecycleOwner,
        ) {
            val contributionListAdapter = ContributionListAdapter(requireContext(), it)
            listView.adapter = contributionListAdapter
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
    }

    private fun showLoading(view: View) {
        val loadingProgressBar: ProgressBar = view.findViewById(R.id.progress_circular)
        loadingProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading(view: View) {
        val loadingProgressBar: ProgressBar = view.findViewById(R.id.progress_circular)
        loadingProgressBar.visibility = View.GONE
    }
}