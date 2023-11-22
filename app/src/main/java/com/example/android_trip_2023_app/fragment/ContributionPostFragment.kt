package com.example.android_trip_2023_app.fragment

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.databinding.FragmentContributionPostBinding
import com.example.android_trip_2023_app.model.QuestData
import com.example.android_trip_2023_app.view_model.ContributionPostsViewModel
import java.io.IOException
import java.io.InputStream

class ContributionPostFragment : Fragment() {

    private lateinit var viewModel: ContributionPostsViewModel
    private lateinit var binding: FragmentContributionPostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentContributionPostBinding.inflate(
                inflater, container, false
            )
        viewModel = ViewModelProvider(this)[ContributionPostsViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val imageData = requireActivity().intent.getParcelableExtra<Uri>("image_data")
        val questData = requireActivity().intent.getSerializableExtra("quest_data") as QuestData?
        val areaName = requireActivity().intent.getStringExtra("area_name")

        if (imageData != null) {
            val imageView = view.findViewById<ImageView>(R.id.camera_image)
            imageView.setImageURI(imageData)
        }

        val activityTitleView = view.findViewById<TextView>(R.id.activity_title)
        if (questData != null) {
            activityTitleView.text = questData.questionName
        } else {
            activityTitleView.text = context?.getString(R.string.free_contribution)
        }

        val backButton: ImageView = view.findViewById(R.id.back_button)
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
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

        viewModel.completeDialogMsg.observe(
            viewLifecycleOwner,
        ) { msg ->
            if (msg.isNotEmpty()) {
                AlertDialog.Builder(context, R.style.AlertDialog)
                    .setMessage(msg)
                    .setPositiveButton("はい") { _, _ ->
                        requireActivity().onBackPressed()
                    }
                    .show()
            }
        }

        viewModel.confirmDialogMsg.observe(
            viewLifecycleOwner,
        ) { msg ->
            if (msg.isNotEmpty()) {
                // ダイアログを作成
                val builder = AlertDialog.Builder(context, R.style.AlertDialog)

                builder.setTitle(msg)
                if(imageData != null){
                    builder.setPositiveButton("はい") { _, _ ->
                        viewModel.activityContribution(
                            areaName,
                            uriToBitmap(requireContext(), imageData)!!,
                            questData,
                            requireContext()
                        )
                    }
                }


                builder.setNegativeButton("いいえ") { _, _ ->
                    requireActivity().onBackPressed()
                }

                builder.show()
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

    private fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        var imageStream: InputStream? = null
        // ContentResolverを使用してUriからInputStreamを取得
        imageStream = context.contentResolver.openInputStream(uri)

        // InputStreamを使用してBitmapを生成
        val originalBitmap = BitmapFactory.decodeStream(imageStream)
        try {
            // Exif情報を取得して画像を修正
            val exif = ExifInterface(uri.path!!)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            return rotateBitmap(originalBitmap, orientation)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                imageStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }


    // 画像を回転させるメソッド
    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


}
