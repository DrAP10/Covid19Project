package com.example.model.bo

import java.util.*


data class CovidTotalDataBo(
    val date: Date?,
    val name: String,
    val confirmed: Int,
    val deaths: Int,
)