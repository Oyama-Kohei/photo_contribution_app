package com.example.android_trip_2023_app.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.ContributionResponse
import com.example.android_trip_2023_app.model.PointSummaryResponse
import com.example.android_trip_2023_app.repository.ContributionApiRepositoryImpl
import com.example.android_trip_2023_app.utils.ResultHandler
import kotlinx.coroutines.launch

class ContributionViewModel : ViewModel() {
    val contributionData: LiveData<List<ContributionResponse>> get() = _contributionData
    val onLoading: LiveData<Boolean> get() = _onLoading
    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg

    private var _contributionData = MutableLiveData<List<ContributionResponse>>()
    private var _onLoading = MutableLiveData<Boolean>()
    private var _errorDialogMsg = MutableLiveData<String>()

    var repository = ContributionApiRepositoryImpl()

    fun init(context: Context) {
        fetchContribution(context)
    }

    private fun fetchContribution(context: Context) {
        viewModelScope.launch {
            try {
                _onLoading.postValue(true)
                when (val result = repository.getContribution()) {
                    is ResultHandler.Success<List<ContributionResponse>> -> {
                        if (result.data != null) {
                            _contributionData.postValue(result.data!!)
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