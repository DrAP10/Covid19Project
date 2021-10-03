package com.example.model.bo

import java.io.Serializable
import java.util.*


data class PlaceDataBo(
    val id: String,
    val name: String,
    var date: Date?,
    var confirmed: Int,
    var deaths: Int,
    var newConfirmed: Int,
    var newDeaths: Int,
    var newOpenCases: Int,
    var newRecovered: Int,
) : Serializable