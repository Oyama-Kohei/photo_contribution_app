package com.example.android_trip_2023_app.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.adapter.TeamListAdapter
import com.example.android_trip_2023_app.databinding.FragmentTeamBinding
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

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.team_list)

        viewModel.teamData.observe(
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
    }
}