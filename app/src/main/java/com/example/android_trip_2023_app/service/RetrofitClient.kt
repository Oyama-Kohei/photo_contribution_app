package com.example.android_trip_2023_app.service

import com.example.android_trip_2023_app.BuildConfig
import com.example.android_trip_2023_app.BuildConfig.TIMEOUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    var teamApiService: TeamApiService = retrofit.create(TeamApiService::class.java)
    var pointSummaryApiService: PointSummaryApiService = retrofit.create(PointSummaryApiService::class.java)
    var questApiService: QuestApiService = retrofit.create(QuestApiService::class.java)
    var contributionApiService: ContributionApiService = retrofit.create(ContributionApiService::class.java)
}
