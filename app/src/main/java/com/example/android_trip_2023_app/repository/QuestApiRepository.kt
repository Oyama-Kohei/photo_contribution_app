package com.example.android_trip_2023_app.repository
import com.example.android_trip_2023_app.model.QuestResponse
import com.example.android_trip_2023_app.service.RetrofitClient
import com.example.android_trip_2023_app.utils.ResultHandler
import com.example.android_trip_2023_app.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface QuestApiRepository {
    suspend fun getQuest(): ResultHandler<List<QuestResponse>>
}

class QuestApiRepositoryImpl(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : QuestApiRepository {
    override suspend fun getQuest(): ResultHandler<List<QuestResponse>> {
        return safeApiCall(dispatcher, listOf<QuestResponse>() ::class.java) {
            RetrofitClient.questApiService.getQuest().execute()
        }
    }
}
