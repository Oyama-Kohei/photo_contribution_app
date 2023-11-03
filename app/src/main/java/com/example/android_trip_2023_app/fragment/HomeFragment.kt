package com.example.android_trip_2023_app.fragment

import android.app.ActionBar
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.databinding.FragmentHomeBinding
import com.example.android_trip_2023_app.view_model.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Objects

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = view.findViewById(R.id.home_bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener {
            viewModel.onNavigationItemSelected(it.itemId)
            true
        }

        // 初期画面として最初のフラグメントを表示
        if (savedInstanceState == null) {
            val initialFragment = ActivityFragment()
            replaceFragment(initialFragment)
        }

        viewModel.selectedItemId.observe(
            viewLifecycleOwner,
        ) {
            when (it) {
                R.id.navigation_activity -> {
                    val fragment = ActivityFragment()
                    replaceFragment(fragment)
                }
                R.id.navigation_team -> {
                    val fragment = TeamFragment()
                    replaceFragment(fragment)
                }
                R.id.navigation_contribution -> {
                    val fragment = ContributionFragment()
                    replaceFragment(fragment)
                }
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
    }

    private fun replaceFragment(fragment: Fragment) {
        val activityFragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = activityFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}