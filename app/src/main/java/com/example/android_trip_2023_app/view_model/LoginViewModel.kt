package com.example.android_trip_2023_app.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.LoginResponse
import com.example.android_trip_2023_app.repository.LoginApiRepositoryImpl
import com.example.android_trip_2023_app.utils.ResultHandler.*
import com.example.android_trip_2023_app.utils.Validator
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val onTransit: LiveData<Boolean> get() = _onTransit
    val errorDialogMsg: LiveData<String> get() = _errorDialogMsg

    val id = MutableLiveData("")
    val password = MutableLiveData("")
    var idErrorMsg = MutableLiveData("")
    var passwordErrorMsg = MutableLiveData("")

    private var _onTransit = MutableLiveData<Boolean>()
    private var _errorDialogMsg = MutableLiveData<String>()

    var repository = LoginApiRepositoryImpl()
    var validator = Validator()

    fun login(context: Context) {
        _onTransit.postValue(true)
        return

        // バリデーションチェック処理
        val mailValidationResult = validator.mailValidation(id.value.toString())
        val passValidationResult = validator.passwordValidation(password.value.toString())
        idErrorMsg.postValue(mailValidationResult)
        passwordErrorMsg.postValue(passValidationResult)
        if (mailValidationResult.isNotEmpty() || passValidationResult.isNotEmpty()) {
            return
        }
        viewModelScope.launch {
            // APIを呼び出す
            try {
                when (val result = repository.postLogin(id.value.toString(), password.value.toString())) {
                    is Success<LoginResponse> -> _onTransit.postValue(true)
                    is UnauthorisedResponse<LoginResponse> ->
                        if(result.data != null) {
                            _errorDialogMsg.postValue(result.data.message)
                        } else {
                            _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
                        }
                    is NetworkException -> _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
                    is TimeoutException -> _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
                    is DecodeException -> _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
                    is UnexpectedException -> _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
                }
            } catch (e: Exception) {
                _errorDialogMsg.postValue(context.getString(R.string.unexpected_error_dialog))
            }
        }
    }
}
