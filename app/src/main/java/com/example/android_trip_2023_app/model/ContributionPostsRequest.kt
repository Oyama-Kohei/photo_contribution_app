package com.example.android_trip_2023_app.model

data class ContributionPostsRequest(
    val teamId: String,
    val file: String,
    val areaName: String?,
    val questId: String?,
    val point: Float?,
    val isNeedAi: Boolean?,
)
