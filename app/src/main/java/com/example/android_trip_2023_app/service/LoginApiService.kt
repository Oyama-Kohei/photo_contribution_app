package com.example.android_trip_2023_app.service

import com.example.android_trip_2023_app.BuildConfig
import com.example.android_trip_2023_app.model.LoginRequest
import com.example.android_trip_2023_app.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

interface LoginApiService {

    @Headers("X-API-Key:${BuildConfig.API_KEY}", "Content-Type: application/json")
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
