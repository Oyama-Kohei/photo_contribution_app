package com.example.android_trip_2023_app.fragment

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.databinding.FragmentContributionPostBinding
import com.example.android_trip_2023_app.view_model.ContributionPostsViewModel

class ContributionPostFragment : Fragment() {

    private lateinit var viewModel: ContributionPostsViewModel
    private lateinit var binding: FragmentContributionPostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContributionPostBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ContributionPostsViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        val imageData = requireActivity().intent.getParcelableExtra<Bitmap>("image_data")
        val activityTitle = requireActivity().intent.getStringExtra("activity_title")
        if (imageData != null) {
            val imageView = view.findViewById<ImageView>(R.id.camera_image)
            imageView.setImageBitmap(imageData)
        }

        val activityTitleView = view.findViewById<TextView>(R.id.activity_title)
        if (activityTitle != null) {
            activityTitleView.text = activityTitle
        } else {
            activityTitleView.text = context?.getString(R.string.free_contribution)
        }

        val backButton: ImageView = view.findViewById(R.id.back_button)
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.confirmDialogMsg.observe(
            viewLifecycleOwner,
        ) { msg ->
            if (msg.isNotEmpty()) {
                // ダイアログを作成
                val builder = AlertDialog.Builder(context, R.style.AlertDialog)

                builder.setTitle(msg)
                builder.setPositiveButton("はい") { _, _ ->
                    viewModel.activityContribution(context)
                }

                builder.setNegativeButton("いいえ") { _, _ ->
                    requireActivity().onBackPressed()
                }

                builder.show()
            }
        }
    }
}
