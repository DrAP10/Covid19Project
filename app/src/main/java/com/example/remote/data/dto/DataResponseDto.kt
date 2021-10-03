package com.example.remote.data.dto

data class DataResponseDto(
    val dates: Map<String, DataDto>,
    val total: CovidTotalDataDto,
)
