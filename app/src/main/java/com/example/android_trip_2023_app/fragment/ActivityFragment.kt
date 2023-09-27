package com.example.android_trip_2023_app.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.adapter.AreaActivityListAdapter
import com.example.android_trip_2023_app.databinding.FragmentActivityBinding
import com.example.android_trip_2023_app.view_model.ActivityViewModel

class ActivityFragment : Fragment() {
    companion object {
        const val CAMERA_REQUEST_CODE = 1
        const val CAMERA_PERMISSION_REQUEST_CODE = 2
    }

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
            val areaActivityListAdapter = AreaActivityListAdapter(requireContext(), it, viewModel)
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

        viewModel.activeCameraFlag.observe(
            viewLifecycleOwner,
        ) {
            if(it) {
                launchCamera()
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun launchCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            if (resolveActivity(requireActivity().packageManager) != null) {
                if (checkCameraPermission()) {
                    takePicture()
                } else {
                    grantCameraPermission()
                }
            }
        }
    }

    private fun checkCameraPermission() = PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(requireActivity().applicationContext, Manifest.permission.CAMERA)

    private fun grantCameraPermission() =
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE)

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture()
            }
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // ここで画像が撮影された後の処理を行います
        }
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        takePictureLauncher.launch(intent)
    }
}