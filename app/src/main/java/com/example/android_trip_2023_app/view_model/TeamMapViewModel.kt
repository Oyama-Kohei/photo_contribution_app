package com.example.android_trip_2023_app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamMapViewModel : ViewModel() {
    val onTransitPoint: LiveData<Boolean> get() = _onTransitPoint

    private var _onTransitPoint = MutableLiveData<Boolean>()

    fun onTapPoint() {
        _onTransitPoint.postValue(true)
    }
}