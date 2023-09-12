package com.example.android_trip_2023_app.fragment

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.adapter.AreaActivityListAdapter
import com.example.android_trip_2023_app.databinding.FragmentActivityBinding
import com.example.android_trip_2023_app.view_model.ActivityViewModel

class ActivityFragment : Fragment() {

    private lateinit var viewModel: ActivityViewModel
    private lateinit var binding: FragmentActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentActivityBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.area_activity_list)

        viewModel.activityData.observe(
            viewLifecycleOwner,
        ) {
            val areaActivityListAdapter = AreaActivityListAdapter(requireContext(), it)
            listView.adapter = areaActivityListAdapter
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