package com.example.android_trip_2023_app.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.adapter.TeamListAdapter
import com.example.android_trip_2023_app.databinding.FragmentTeamBinding
import com.example.android_trip_2023_app.view_model.TeamMapViewModel
import com.example.android_trip_2023_app.view_model.TeamViewModel

class TeamFragment : Fragment() {
    private lateinit var viewModel: TeamViewModel
    private lateinit var binding: FragmentTeamBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTeamBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[TeamViewModel::class.java]
        viewModel.init(requireContext())

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.team_list)

        viewModel.pointSummaryData.observe(
            viewLifecycleOwner,
        ) {
            val teamListAdapter = TeamListAdapter(requireContext(), it)
            listView.adapter = teamListAdapter
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

        viewModel.onTransitMap.observe(
            viewLifecycleOwner,
        ) {
            if (it) {
                val initialFragment = TeamMapFragment()
                val bundle = Bundle().apply {
                    putString("mountain_area_color", viewModel.getMountainTeamColor(requireContext()))
                    putString("makiba_area_color", viewModel.getMakibaTeamColor(requireContext()))
                    putString("fruit_area_color", viewModel.getFruitTeamColor(requireContext()))
                    putString("wakuwaku_area_color", viewModel.getWakuwakuTeamColor(requireContext()))
                }
                initialFragment.arguments = bundle
                replaceFragment(initialFragment)
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

    private fun replaceFragment(fragment: Fragment) {
        val activityFragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = activityFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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