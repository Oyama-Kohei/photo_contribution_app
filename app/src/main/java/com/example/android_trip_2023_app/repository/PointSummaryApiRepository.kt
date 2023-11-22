package com.example.android_trip_2023_app.repository
import com.example.android_trip_2023_app.model.PointSummaryResponse
import com.example.android_trip_2023_app.service.RetrofitClient
import com.example.android_trip_2023_app.utils.ResultHandler
import com.example.android_trip_2023_app.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface PointSummaryApiRepository {
    suspend fun getPointSummary(): ResultHandler<List<PointSummaryResponse>>
}

class PointSummaryApiRepositoryImpl(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : PointSummaryApiRepository {
    override suspend fun getPointSummary(): ResultHandler<List<PointSummaryResponse>> {
        return safeApiCall(dispatcher, listOf<PointSummaryResponse>()::class.java) {
            RetrofitClient.pointSummaryApiService.getPointSummary().execute()
        }
    }
}
