package com.example.android_trip_2023_app.ui.main

import android.content.Context
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.repository.LoginApiRepositoryImpl
import com.example.android_trip_2023_app.utils.ResultHandler
import com.example.android_trip_2023_app.utils.TestCoroutinesRule
import com.example.android_trip_2023_app.utils.Validator
import com.example.android_trip_2023_app.utils.getOrAwaitValue
import com.example.android_trip_2023_app.view_model.LoginViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutinesRule = TestCoroutinesRule()

    @Mock
    private lateinit var mockBooleanObserver: Observer<Boolean>

    @Mock
    private lateinit var mockStringObserver: Observer<String>

    @Mock
    private lateinit var mockRepository: LoginApiRepositoryImpl

    @Mock
    private lateinit var mockContext: Context

    private val testId = "test@example.com"
    private val testPassword = "password"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = LoginViewModel()
        viewModel.repository = mockRepository
        viewModel.onTransit.observeForever(mockBooleanObserver)
        viewModel.errorDialogMsg.observeForever(mockStringObserver)
        viewModel.idErrorMsg.observeForever(mockStringObserver)
        viewModel.passwordErrorMsg.observeForever(mockStringObserver)
    }

    // 正常系
    @Test
    fun `正常系`() = runTest {
        viewModel.id.postValue(testId)
        viewModel.password.postValue(testPassword)
        whenever(viewModel.repository.postLogin(any(), any())).thenReturn(
            ResultHandler.Success(LoginResponse("200", ""))
        )
        viewModel.login(mockContext)
        assertTrue(viewModel.onTransit.getOrAwaitValue())
    }

    @Test
    fun `異常系：メールアドレス入力エラー`() = runTest {
        val mailAddressMessage = "メールアドレスを入力してください。"

        whenever(Validator().mailValidation(any())).thenReturn(
            mailAddressMessage
        )
        viewModel.login(mockContext)

        assertEquals(viewModel.idErrorMsg.getOrAwaitValue(), mailAddressMessage)
        assertFalse(viewModel.onTransit.getOrAwaitValue())
    }

    @Test
    fun `異常系：パスワード入力エラー`() = runTest {
        val passErrorMessage = "パスワードを入力してください。"

        whenever(Validator().passwordValidation(any())).thenReturn(
            passErrorMessage
        )
        viewModel.login(mockContext)

        assertEquals(viewModel.passwordErrorMsg.getOrAwaitValue(), passErrorMessage)
        assertFalse(viewModel.onTransit.getOrAwaitValue())
    }

    @Test
    fun `異常系： メールアドレスパスワード入力エラー`() = runTest {
        val mailAddressMessage = "メールアドレスを入力してください。"
        val passErrorMessage = "パスワードを入力してください。"

        whenever(Validator().mailValidation(any())).thenReturn(
            mailAddressMessage
        )
        whenever(Validator().passwordValidation(any())).thenReturn(
            passErrorMessage
        )

        viewModel.login(mockContext)

        assertEquals(viewModel.idErrorMsg.getOrAwaitValue(), mailAddressMessage)
        assertEquals(viewModel.passwordErrorMsg.getOrAwaitValue(), passErrorMessage)
        assertFalse(viewModel.onTransit.getOrAwaitValue())
    }

    @Test
    fun `異常系：UnAuthorizedException`() = runTest {
        val testMessage = "メールアドレスまたはパスワードが正しくありません。"
        viewModel.id.postValue(testId)
        viewModel.password.postValue(testPassword)
        whenever(viewModel.repository.postLogin(any(), any())).thenReturn(
            ResultHandler.UnauthorisedResponse(LoginResponse("401", testMessage))
        )
        viewModel.login(mockContext)

        assertEquals(viewModel.errorDialogMsg.getOrAwaitValue(), testMessage)
    }

    @Test
    fun `異常系：NetworkException`() = runTest {
        viewModel.id.postValue(testId)
        viewModel.password.postValue(testPassword)

        whenever(viewModel.repository.postLogin(any(), any())).thenReturn(
            ResultHandler.NetworkException
        )

        viewModel.login(mockContext)
        assertEquals(viewModel.errorDialogMsg.getOrAwaitValue(), mockContext.getString(R.string.unexpected_error_dialog))
    }

    @Test
    fun `異常系：TimeoutException`() = runTest {
        viewModel.id.postValue(testId)
        viewModel.password.postValue(testPassword)

        whenever(viewModel.repository.postLogin(any(), any())).thenReturn(
            ResultHandler.TimeoutException
        )

        viewModel.login(mockContext)
        assertEquals(viewModel.errorDialogMsg.getOrAwaitValue(), mockContext.getString(R.string.unexpected_error_dialog))
    }

    @Test
    fun `異常系：DecodeException`() = runTest {
        viewModel.id.postValue(testId)
        viewModel.password.postValue(testPassword)

        whenever(viewModel.repository.postLogin(any(), any())).thenReturn(
            ResultHandler.DecodeException
        )

        viewModel.login(mockContext)
        assertEquals(viewModel.errorDialogMsg.getOrAwaitValue(), mockContext.getString(R.string.unexpected_error_dialog))
    }

    @Test
    fun `異常系：UnexpectedException`() = runTest {
        viewModel.id.postValue(testId)
        viewModel.password.postValue(testPassword)

        whenever(viewModel.repository.postLogin(any(), any())).thenReturn(
            ResultHandler.UnexpectedException
        )

        viewModel.login(mockContext)
        assertEquals(viewModel.errorDialogMsg.getOrAwaitValue(), mockContext.getString(R.string.unexpected_error_dialog))
    }

    @Test
    fun `異常系：その他Exception`() = runTest {
        viewModel.id.postValue(testId)
        viewModel.password.postValue(testPassword)

        whenever(viewModel.repository.postLogin(any(), any())).thenThrow(
            Resources.NotFoundException()
        )
        viewModel.login(mockContext)

        assertEquals(viewModel.errorDialogMsg.getOrAwaitValue(), mockContext.getString(R.string.unexpected_error_dialog))
    }
}
