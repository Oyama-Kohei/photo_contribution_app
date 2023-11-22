package com.example.android_trip_2023_app.service

import com.example.android_trip_2023_app.BuildConfig
import com.example.android_trip_2023_app.model.PointSummaryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import java.util.*

interface PointSummaryApiService {

    @Headers("X-API-Key:${BuildConfig.API_KEY}", "Content-Type: application/json")
    @GET("PointSummary")
    fun getPointSummary(): Call<List<PointSummaryResponse>>
}
