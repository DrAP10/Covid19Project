package com.example.remote.data.dto

import com.google.gson.annotations.SerializedName

data class CovidTotalDataDto(
    val date: String,
    val name: String,
    @SerializedName("today_confirmed") val todayConfirmed: Int,
    @SerializedName("today_deaths") val todayDeaths: Int,
)