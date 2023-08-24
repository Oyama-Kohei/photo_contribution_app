package com.example.android_trip_2023_app.utils

sealed class ResultHandler<out R> {

    // 成功時の返却値
    data class Success<T>(val data: T?) : ResultHandler<T>()

    // 認証エラー
    data class UnauthorisedResponse<T>(val data: T?) : ResultHandler<T>()

    // ネットワークエラー
    object NetworkException : ResultHandler<Nothing>()

    // タイムアウトエラー
    object TimeoutException : ResultHandler<Nothing>()

    // デコードエラー
    object DecodeException : ResultHandler<Nothing>()

    // 想定外エラー
    object UnexpectedException : ResultHandler<Nothing>()
}
