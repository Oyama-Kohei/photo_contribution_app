package com.example.android_trip_2023_app.utils

import android.content.res.Resources.NotFoundException
import com.google.gson.JsonSyntaxException
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class SafeApiCallTest {

    data class TestData(val message: String)

    @Test
    fun success() = runBlocking {
        val mockApiResponse = Response.success(TestData("Test"))
        val apiCallMock: suspend () -> Response<TestData> = {
            mockApiResponse
        }

        val result = safeApiCall(Dispatchers.Default, TestData::class.java, apiCallMock)

        assertTrue(result is ResultHandler.Success)
        val successResult = result as ResultHandler.Success
        assertEquals(successResult.data?.message, "Test")
    }

    @Test
    fun error_unauthorisedResponse() = runBlocking {
        val expectedErrorMessage = "Unauthorized error message"
        val errorResponseBody = "{\"message\":\"$expectedErrorMessage\"}"
        val mediaType = MediaType.get("application/json")
        val mockApiResponse = Response.error<TestData>(
            401,
            ResponseBody.create(mediaType, errorResponseBody),
        )
        val apiCallMock: suspend () -> Response<TestData> = {
            mockApiResponse
        }

        val result = safeApiCall(Dispatchers.Default, TestData::class.java, apiCallMock)

        assertTrue(result is ResultHandler.UnauthorisedResponse)

        val unauthorisedResponse = result as ResultHandler.UnauthorisedResponse
        assertTrue(unauthorisedResponse.data?.message == expectedErrorMessage)
    }

    @Test
    fun error_timeoutException() = runBlocking {
        val apiCallMock: suspend () -> Response<TestData> = {
            throw SocketTimeoutException()
        }

        val result = safeApiCall(Dispatchers.Default, TestData::class.java, apiCallMock)

        assertEquals(result, ResultHandler.TimeoutException)
    }

    @Test
    fun error_networkException() = runBlocking {
        val apiCallMock: suspend () -> Response<TestData> = {
            throw IOException()
        }

        val result = safeApiCall(Dispatchers.Default, TestData::class.java, apiCallMock)

        assertEquals(result, ResultHandler.NetworkException)
    }

    @Test
    fun error_decodeException() = runBlocking {
        val apiCallMock: suspend () -> Response<TestData> = {
            throw JsonSyntaxException("")
        }

        val result = safeApiCall(Dispatchers.Default, TestData::class.java, apiCallMock)

        assertEquals(result, ResultHandler.DecodeException)
    }

    @Test
    fun error_unexpectedException() = runBlocking {
        val mockApiResponse = Response.error<TestData>(500, mockk(relaxed = true))
        val apiCallMock: suspend () -> Response<TestData> = {
            mockApiResponse
        }

        val result = safeApiCall(Dispatchers.Default, TestData::class.java, apiCallMock)

        assertEquals(result, ResultHandler.UnexpectedException)
    }

    @Test
    fun error_unexpectedException_not_handling() = runBlocking {
        val apiCallMock: suspend () -> Response<TestData> = {
            throw NotFoundException()
        }

        val result = safeApiCall(Dispatchers.Default, TestData::class.java, apiCallMock)

        assertEquals(result, ResultHandler.UnexpectedException)
    }
}
