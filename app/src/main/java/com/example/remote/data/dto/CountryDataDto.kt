package com.example.remote.data.dto

import com.google.gson.annotations.SerializedName

data class CountryDataDto(
    val id: String,
    val name: String,
    val date: String,
    @SerializedName("today_confirmed") val todayConfirmed: Int,
    @SerializedName("today_death") val todayDeath: Int,
    @SerializedName("today_new_confirmed") val todayNewConfirmed: Int,
    @SerializedName("today_new_deaths") val todayNewDeaths: Int,
    @SerializedName("today_new_open_cases") val todayNewOpenCases: Int,
    @SerializedName("today_new_recovered") val todayNewRecovered: Int,
) {
}