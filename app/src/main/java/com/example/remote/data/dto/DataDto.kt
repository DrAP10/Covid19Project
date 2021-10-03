package com.example.remote.data.dto

data class DataDto(
    val countries: Map<String, CountryDataDto>,
    val info: InfoDataDto,
)