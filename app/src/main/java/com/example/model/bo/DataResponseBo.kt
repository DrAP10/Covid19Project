package com.example.model.bo

data class DataResponseBo(
    val dates: Map<String, DataBo>,
    val total: CovidTotalDataBo,
)
