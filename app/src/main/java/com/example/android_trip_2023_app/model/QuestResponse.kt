package com.example.android_trip_2023_app.model
import java.io.Serializable

data class QuestResponse(
    val questions: List<QuestData>,
    val areaName: String,
    val id: String,
)

data class QuestData(
    val activityId: String,
    val point: Int?,
    val caption: String?,
    val questionName: String,
) : Serializable


