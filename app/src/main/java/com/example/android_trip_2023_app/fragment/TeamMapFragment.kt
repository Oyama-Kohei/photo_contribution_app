package com.example.android_trip_2023_app.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.databinding.FragmentTeamMapBinding
import com.example.android_trip_2023_app.view_model.TeamMapViewModel

class TeamMapFragment : Fragment() {
    private lateinit var viewModel: TeamMapViewModel
    private lateinit var binding: FragmentTeamMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTeamMapBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[TeamMapViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pinIconMountain: ImageView = view.findViewById(R.id.pin_icon_mountain)
        val pinIconMakiba: ImageView = view.findViewById(R.id.pin_icon_makiba)
        val pinIconFruit: ImageView = view.findViewById(R.id.pin_icon_fruit)
        val pinIconWakuwaku: ImageView = view.findViewById(R.id.pin_icon_wakuwaku)

        val mountainAreaColor: String = arguments?.getString("mountain_area_color")!!
        val makibaAreaColor: String = arguments?.getString("makiba_area_color")!!
        val fruitAreaColor: String = arguments?.getString("fruit_area_color")!!
        val wakuwakuAreaColor: String = arguments?.getString("wakuwaku_area_color")!!

        val circleDrawableMountain = ContextCompat.getDrawable(requireActivity(), R.drawable.team_icon_pin) as GradientDrawable
        circleDrawableMountain.setColor(Color.parseColor(mountainAreaColor))
        pinIconMountain.background = circleDrawableMountain

        val circleDrawableMakiba = ContextCompat.getDrawable(requireActivity(), R.drawable.team_icon_pin_makiba) as GradientDrawable
        circleDrawableMakiba.setColor(Color.parseColor(makibaAreaColor))
        pinIconMakiba.background = circleDrawableMakiba

        val circleDrawableFruit = ContextCompat.getDrawable(requireActivity(), R.drawable.team_icon_pin_fruit) as GradientDrawable
        circleDrawableFruit.setColor(Color.parseColor(fruitAreaColor))
        pinIconFruit.background = circleDrawableFruit

        val circleDrawableWakuwaku = ContextCompat.getDrawable(requireActivity(), R.drawable.team_icon_pin_wakuwaku) as GradientDrawable
        circleDrawableWakuwaku.setColor(Color.parseColor(wakuwakuAreaColor))
        pinIconWakuwaku.background = circleDrawableWakuwaku

        viewModel.onTransitPoint.observe(
            viewLifecycleOwner,
        ) {
            if (it) {
                val initialFragment = TeamFragment()
                replaceFragment(initialFragment)
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