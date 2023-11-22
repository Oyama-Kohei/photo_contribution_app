package com.example.android_trip_2023_app.service

import com.example.android_trip_2023_app.BuildConfig
import com.example.android_trip_2023_app.model.QuestResponse
import com.example.android_trip_2023_app.model.TeamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import java.util.*

interface QuestApiService {

    @Headers("X-API-Key:${BuildConfig.API_KEY}", "Content-Type: application/json")
    @GET("Quest")
    fun getQuest(): Call<List<QuestResponse>>
}
