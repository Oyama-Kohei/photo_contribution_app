package com.example.android_trip_2023_app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg
    val selectedItemId: LiveData<Int> get() = _selectedItemId

    private var _errorDialogMsg = MutableLiveData<String>()
    private var _selectedItemId = MutableLiveData<Int>()

    fun onNavigationItemSelected(itemId: Int) {
        _selectedItemId.value = itemId
        return
    }
}