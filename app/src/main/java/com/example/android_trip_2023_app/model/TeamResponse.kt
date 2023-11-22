package com.example.android_trip_2023_app.model

data class TeamResponse(
    val id: String,
    val teamName: String,
    val teamColor: String,
    val member: List<String>,
)
