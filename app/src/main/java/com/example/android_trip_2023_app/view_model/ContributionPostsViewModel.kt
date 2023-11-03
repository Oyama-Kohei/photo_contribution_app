package com.example.android_trip_2023_app.view_model

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_trip_2023_app.R

class ContributionPostsViewModel : ViewModel() {

    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg
    val confirmDialogMsg: LiveData<String> get() = _confirmDialogMsg

    private var _errorDialogMsg = MutableLiveData<String>()
    private var _confirmDialogMsg = MutableLiveData<String>()

    fun activityContribution(context: Context?) {
        Log.d(TAG, "投稿機能")
        return
    }

    fun onTapContributionButton(context: Context) {
        Log.d(TAG, "投稿ボタンタップ")
        _confirmDialogMsg.postValue(context.getString(R.string.contribution_confirm_dialog))
        return
    }
}