package com.example.android_trip_2023_app.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, type: Type, apiCall: suspend () -> Response<T>): ResultHandler<T> {
    return withContext(dispatcher) {
        try {
            val service = apiCall()
            if (service.isSuccessful) {
                val resultResponse = service.body()
                return@withContext ResultHandler.Success(resultResponse)
            } else if (service.code() == 401) {
                val unAuthorisedResponse = Gson().fromJson<T>(service.errorBody()?.string(), type)
                return@withContext ResultHandler.UnauthorisedResponse(unAuthorisedResponse)
            } else {
                return@withContext ResultHandler.UnexpectedException
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> ResultHandler.TimeoutException
                is IOException -> ResultHandler.NetworkException
                is JsonSyntaxException -> ResultHandler.DecodeException
                else -> {
                    ResultHandler.UnexpectedException
                }
            }
        }
    }
}
