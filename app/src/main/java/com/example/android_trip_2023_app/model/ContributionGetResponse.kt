package com.example.android_trip_2023_app.model

data class ContributionGetResponse(
    val contribution_id: String,
    val image_url: String,
    val team_name: String,
    val icon: String,
    val contribution_point: Int,
)
