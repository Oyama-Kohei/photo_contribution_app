package com.example.android_trip_2023_app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_trip_2023_app.model.ActivityData
import com.example.android_trip_2023_app.model.ActivityResponse

class TeamViewModel : ViewModel() {
    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg

    private var _errorDialogMsg = MutableLiveData<String>()

}