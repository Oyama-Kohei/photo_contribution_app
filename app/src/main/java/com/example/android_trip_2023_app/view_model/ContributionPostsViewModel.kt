package com.example.android_trip_2023_app.view_model

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.ContributionPostsRequest
import com.example.android_trip_2023_app.model.QuestData
import com.example.android_trip_2023_app.repository.ContributionApiRepositoryImpl
import com.example.android_trip_2023_app.utils.DatabaseHelper
import com.example.android_trip_2023_app.utils.ResultHandler
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ContributionPostsViewModel : ViewModel() {

    val onLoading: LiveData<Boolean> get() = _onLoading
    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg
    val completeDialogMsg: LiveData<String> get() = _completeDialogMsg
    val confirmDialogMsg: LiveData<String> get() = _confirmDialogMsg

    private var _onLoading = MutableLiveData<Boolean>()
    private var _errorDialogMsg = MutableLiveData<String>()
    private var _completeDialogMsg = MutableLiveData<String>()
    private var _confirmDialogMsg = MutableLiveData<String>()

    private var repository = ContributionApiRepositoryImpl()

    fun activityContribution(
        areaName: String?,
        imageBitmap: Bitmap,
        questData: QuestData?,
        context: Context
    ) {
        val imageBase64 = bitmapToBase64(imageBitmap)
        val userData = DatabaseHelper(context).getUserData()
        if (userData?.teamId == null) {
            throw Exception()
        }
        viewModelScope.launch {
            try {
                val postData: ContributionPostsRequest
                if (areaName == null && questData == null) {
                    postData = ContributionPostsRequest(
                        userData.teamId,
                        imageBase64,
                        null,
                        null,
                        null,
                        null
                    )
                } else if (questData!!.point == null) {
                    postData = ContributionPostsRequest(
                        userData.teamId,
                        imageBase64,
                        areaName!!,
                        questData.activityId,
                        null,
                        true,
                    )
                } else {
                    postData = ContributionPostsRequest(
                        userData.teamId,
                        imageBase64,
                        areaName,
                        questData.activityId,
                        questData.point!!.toFloat(),
                        false
                    )
                }
                _onLoading.postValue(true)
                when (repository.postContribution(postData)) {
                    is ResultHandler.Success<ContributionPostsRequest> -> {
                        _completeDialogMsg.postValue(context.getString(R.string.contribution_complete_dialog))
                    }

                    else -> _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
                }
                _onLoading.postValue(false)
            } catch (e: Exception) {
                _onLoading.postValue(false)
                _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
            }
        }
    }

    fun onTapContributionButton(context: Context) {
        Log.d(TAG, "投稿ボタンタップ")
        _confirmDialogMsg.postValue(context.getString(R.string.contribution_confirm_dialog))
        return
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        var quality = 100 // 初期の品質
        val maxSizeInBytes = 1 * 1024 * 1024 // 10 MB以下に設定

        // 画像を指定されたサイズ以下になるまで圧縮を試行
        while (true) {
            byteArrayOutputStream.reset() // ByteArrayOutputStreamをリセット

            // BitmapをJPEG形式で指定された品質でByteArrayOutputStreamに圧縮
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)

            // 最終的な画像をBase64に変換
            val compressedBitmapByteArray = byteArrayOutputStream.toByteArray()

            // 圧縮後のサイズがmaxSizeInBytes以下であればループを抜ける
            if (compressedBitmapByteArray.size <= maxSizeInBytes) {
                break
            }

            // 品質を下げる
            quality -= 10
        }

        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    }
}