package com.example.android_trip_2023_app.repository
import com.example.android_trip_2023_app.model.ContributionPostsRequest
import com.example.android_trip_2023_app.model.ContributionResponse
import com.example.android_trip_2023_app.service.RetrofitClient
import com.example.android_trip_2023_app.utils.ResultHandler
import com.example.android_trip_2023_app.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody

interface ContributionApiRepository {
    suspend fun getContribution(): ResultHandler<List<ContributionResponse>>
    suspend fun postContribution(request: ContributionPostsRequest): ResultHandler<ContributionPostsRequest>
}

class ContributionApiRepositoryImpl(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ContributionApiRepository {
    override suspend fun getContribution(): ResultHandler<List<ContributionResponse>> {
        return safeApiCall(dispatcher, listOf<ContributionResponse>()::class.java) {
            RetrofitClient.contributionApiService.getContribution().execute()
        }
    }

    override suspend fun postContribution(request: ContributionPostsRequest): ResultHandler<ContributionPostsRequest> {
        return safeApiCall(dispatcher, ContributionPostsRequest::class.java) {
            RetrofitClient.contributionApiService.postContribution(request).execute()
        }
    }
}
