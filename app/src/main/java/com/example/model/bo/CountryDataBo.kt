package com.example.model.bo

import java.util.*


data class CountryDataBo(
    val id: String,
    val name: String,
    val date: Date?,
    val todayConfirmed: Int,
    val todayDeath: Int,
    val todayNewConfirmed: Int,
    val todayNewDeaths: Int,
    val todayNewOpenCases: Int,
    val todayNewRecovered: Int,
)