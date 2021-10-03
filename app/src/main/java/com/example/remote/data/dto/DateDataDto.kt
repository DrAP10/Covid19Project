package com.example.remote.data.dto

data class DateDataDto(
    val countries: Map<String, PlaceDataDto>,
    val info: InfoDataDto,
)