package com.example.model.bo

data class DataResponseBo(
    val placesData: List<PlaceDataBo>,
    val total: CovidTotalDataBo?,
)
