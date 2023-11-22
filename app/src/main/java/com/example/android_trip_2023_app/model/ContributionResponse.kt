package com.example.android_trip_2023_app.model

data class ContributionResponse(
    val id: String,
    val teamId: String,
    val questId: String?,
    val s3: String,
    val cdn: String,
    val areaName: String?,
    val point: Float,
    val isNeedAi: Boolean?,
)
