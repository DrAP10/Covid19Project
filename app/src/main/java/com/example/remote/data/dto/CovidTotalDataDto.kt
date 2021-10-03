package com.example.remote.data.dto

import com.google.gson.annotations.SerializedName

data class CovidTotalDataDto(
    val date: String,
    val name: String,
    val todayConfirmed: Int,
    val todayNewDeath: Int,
    val todayNewRecovered: Int,
) {
}