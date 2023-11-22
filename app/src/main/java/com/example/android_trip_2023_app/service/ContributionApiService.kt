package com.example.android_trip_2023_app.service

import com.example.android_trip_2023_app.BuildConfig
import com.example.android_trip_2023_app.model.ContributionPostsRequest
import com.example.android_trip_2023_app.model.ContributionResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

interface ContributionApiService {

    @Headers("X-API-Key:${BuildConfig.API_KEY}", "Content-Type: application/json")
    @GET("ImageEntry")
    fun getContribution(): Call<List<ContributionResponse>>

    @Headers("X-API-Key:${BuildConfig.API_KEY}", "Content-Type: application/json")
    @POST("ImageEntry")
    fun postContribution(@Body contributionRequest: ContributionPostsRequest): Call<ContributionPostsRequest>
}
