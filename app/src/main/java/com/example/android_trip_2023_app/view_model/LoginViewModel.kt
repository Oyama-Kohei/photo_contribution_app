package com.example.android_trip_2023_app.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.TeamResponse
import com.example.android_trip_2023_app.repository.TeamApiRepositoryImpl
import com.example.android_trip_2023_app.utils.DatabaseHelper
import com.example.android_trip_2023_app.utils.ResultHandler.*
import com.example.android_trip_2023_app.utils.Validator
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val onTransit: LiveData<Boolean> get() = _onTransit
    val onLoading: LiveData<Boolean> get() = _onLoading

    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg

    val id = MutableLiveData("")
    val password = MutableLiveData("")
    var idErrorMsg = MutableLiveData("")

    private var _onTransit = MutableLiveData<Boolean>()
    private var _onLoading = MutableLiveData<Boolean>()
    private var _errorDialogMsg = MutableLiveData<String>()

    var repository = TeamApiRepositoryImpl()
    private var validator = Validator()

    fun init(context: Context) {
        checkLogin(context)
    }

    fun login(context: Context) {
        // バリデーションチェック処理
        val mailValidationResult = validator.mailValidation(id.value.toString())
        idErrorMsg.postValue(mailValidationResult)
        if (mailValidationResult.isNotEmpty()) {
            return
        }
        viewModelScope.launch {
            // APIを呼び出す
            try {
                _onLoading.postValue(true)
                when (val result = repository.getTeam()) {
                    is Success<List<TeamResponse>> -> {
                        if(result.data != null) {
                            var loginFlag = false
                            for (teamData in result.data) {
                                DatabaseHelper(context).insertTeamData(teamData.id, teamData.teamName, teamData.teamColor)
                                if(checkUserTeam(teamData.member)) {
                                    DatabaseHelper(context).insertUserData(id.value.toString(), teamData.id, teamData.teamName, teamData.teamColor)
                                    loginFlag = true
                                }
                            }
                            if(loginFlag) {
                                _onLoading.postValue(false)
                                _onTransit.postValue(true)
                            } else {
                                _errorDialogMsg.postValue(context.getString(R.string.login_error_dialog))
                            }
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

    private fun checkUserTeam(memberList :List<String>): Boolean {
        return memberList.contains(id.value.toString())
    }

    private fun checkLogin(context: Context) {
        val result = DatabaseHelper(context).getUserData()
        if(result?.userId != null) {
            _onTransit.postValue(true)
        }
    }
}
