package com.example.android_trip_2023_app.model

data class ActivityResponse(
    val area_name: String,
    val activity_list: List<ActivityData>,
)

data class ActivityData(
    val activity_id: String,
    val activity_title: String,
    val point: Int,
)


