package com.example.android_trip_2023_app.repository
import com.example.android_trip_2023_app.model.TeamResponse
import com.example.android_trip_2023_app.service.RetrofitClient
import com.example.android_trip_2023_app.utils.ResultHandler
import com.example.android_trip_2023_app.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface TeamApiRepository {
    suspend fun getTeam(): ResultHandler<List<TeamResponse>>
}
class TeamApiRepositoryImpl(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : TeamApiRepository {
    override suspend fun getTeam(): ResultHandler<List<TeamResponse>> {
        return safeApiCall(dispatcher, listOf<TeamResponse>()::class.java) {
            RetrofitClient.teamApiService.getTeam().execute()
        }
    }
}
