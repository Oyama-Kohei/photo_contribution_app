package com.example.android_trip_2023_app.repository
import com.example.android_trip_2023_app.model.LoginRequest
import com.example.android_trip_2023_app.model.LoginResponse
import com.example.android_trip_2023_app.service.RetrofitClient
import com.example.android_trip_2023_app.utils.ResultHandler
import com.example.android_trip_2023_app.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface LoginApiRepository {
    suspend fun postLogin(id: String, pass: String): ResultHandler<LoginResponse>
}

class LoginApiRepositoryImpl(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : LoginApiRepository {
    override suspend fun postLogin(id: String, pass: String): ResultHandler<LoginResponse> {
        return safeApiCall(dispatcher, LoginResponse::class.java) {
            RetrofitClient.loginApiService.login(LoginRequest(id, pass)).execute()
        }
    }
}
