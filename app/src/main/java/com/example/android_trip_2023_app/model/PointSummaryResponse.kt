package com.example.android_trip_2023_app.model

data class PointSummaryResponse(
    val point: Float,
    val id: String,
    val teamId: String,
    val area: List<AreaData>,
)

data class AreaData(
    val areaName: String,
    val point: Float,
)
