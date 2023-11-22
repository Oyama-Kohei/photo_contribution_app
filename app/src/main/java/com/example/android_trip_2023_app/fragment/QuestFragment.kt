package com.example.android_trip_2023_app.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.activity.ContributionPostsActivity
import com.example.android_trip_2023_app.adapter.AreaQuestListAdapter
import com.example.android_trip_2023_app.databinding.FragmentActivityBinding
import com.example.android_trip_2023_app.view_model.QuestViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuestFragment : Fragment() {
    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var viewModel: QuestViewModel
    private lateinit var binding: FragmentActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentActivityBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[QuestViewModel::class.java]
        viewModel.init(requireActivity())

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.area_activity_list)
        val freeButtonView: Button = view.findViewById(R.id.free_button)
        val teamNameView: TextView = view.findViewById(R.id.team_name)
        val teamIcon: ImageView = view.findViewById(R.id.team_icon)
        val userData = viewModel.getUserData(requireContext())
        if (userData != null) {
            val circleDrawable = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.team_icon_oval
            ) as GradientDrawable
            teamNameView.text = userData.teamName
            circleDrawable.setColor(Color.parseColor(userData.teamColor))
            teamIcon.background = circleDrawable
        }

        freeButtonView.setOnClickListener {
            viewModel.dispatchTakePictureIntentFree()
        }

        viewModel.questData.observe(
            viewLifecycleOwner,
        ) {
            val areaQuestListAdapter = AreaQuestListAdapter(requireContext(), it, viewModel)
            listView.adapter = areaQuestListAdapter
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

        viewModel.activeCameraFlag.observe(
            viewLifecycleOwner,
        ) {
            if (it) {
                launchCamera()
            }
        }

        viewModel.contributionImage.observe(
            viewLifecycleOwner,
        ) {
            val intent = ContributionPostsActivity.getIntent(requireContext())
            intent.putExtra("image_data", it)
            intent.putExtra("quest_data", viewModel.targetQuestData)
            intent.putExtra("area_name", viewModel.targetAreaName)
            startActivity(intent)
        }
    }

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

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    private fun checkCameraPermission() = PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(
                requireActivity().applicationContext,
                Manifest.permission.CAMERA
            )

    private fun grantCameraPermission() =
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                takePicture()
            }
        }
    }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
            }
        }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            // 写真を保存するファイルの場所を指定
            val photoFile: File = createImageFile()
            photoFile.let {
                val photoUri = FileProvider.getUriForFile(requireContext(), "com.example.android_trip_2023_app.fileprovider", it)
                putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                // 画像の保存先ファイルパスをViewModelに渡す
                viewModel.setImage(photoFile.toUri())
            }
        }
        takePictureLauncher.launch(intent)
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