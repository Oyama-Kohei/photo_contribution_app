package com.example.android_trip_2023_app.view_model

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.QuestData
import com.example.android_trip_2023_app.model.QuestResponse
import com.example.android_trip_2023_app.model.UserDatabaseModel
import com.example.android_trip_2023_app.repository.QuestApiRepositoryImpl
import com.example.android_trip_2023_app.utils.DatabaseHelper
import com.example.android_trip_2023_app.utils.ResultHandler
import kotlinx.coroutines.launch

class QuestViewModel : ViewModel() {
    val questData: LiveData<List<QuestResponse>> get() = _questData
    val onLoading: LiveData<Boolean> get() = _onLoading

    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg
    val activeCameraFlag: LiveData<Boolean> get() = _activeCameraFlag
    val contributionImage: LiveData<Uri> get() = _contributionImage

    //TODO: もっといい書き方がありそう
    var targetQuestData: QuestData? = null
    var targetAreaName: String? = null

    private var _questData = MutableLiveData<List<QuestResponse>>()
    private var _onLoading = MutableLiveData<Boolean>()
    private var _errorDialogMsg = MutableLiveData<String>()
    private var _activeCameraFlag = MutableLiveData<Boolean>()
    private var _contributionImage = MutableLiveData<Uri>()

    var repository = QuestApiRepositoryImpl()

    fun init(context: Context) {
        fetchQuestData(context)
    }

    fun dispatchTakePictureIntent(questData: QuestData, areaName: String?) {
        targetQuestData = questData
        targetAreaName = areaName
        _activeCameraFlag.value = true
    }

    fun dispatchTakePictureIntentFree() {
        targetQuestData = null
        targetAreaName = null
        _activeCameraFlag.value = true
    }

    fun setImage(uri: Uri) {
        _contributionImage.value = uri
    }

    fun getUserData(context: Context): UserDatabaseModel? {
        return DatabaseHelper(context).getUserData()
    }

    private fun fetchQuestData(context: Context) {
        viewModelScope.launch {
            // APIを呼び出す
            try {
                _onLoading.postValue(true)
                when (val result = repository.getQuest()) {
                    is ResultHandler.Success<List<QuestResponse>> -> {
                        if(result.data != null) {
                            _questData.postValue(result.data!!)
                        }
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
}