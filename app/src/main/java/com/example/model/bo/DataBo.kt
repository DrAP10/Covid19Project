package com.example.model.bo

data class DataBo(
    val countries: Map<String, CountryDataBo>,
    val info: InfoDataBo,
)