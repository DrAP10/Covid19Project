package com.example.remote.data

import com.example.DateUtils
import com.example.model.bo.*
import com.example.remote.data.dto.*
import java.util.*


fun DataResponseDto.toBo(): DataResponseBo {
    val places = mutableListOf<PlaceDataBo>()
    var nearestDate: Date? = null
    dates.forEach {
        val date = DateUtils.getApiDateFormatted(it.key)
        it.value.countries.forEach { placeMap ->
            val placeDto = placeMap.value
            var index = places.indexOfFirst { item -> item.id == placeDto.id }
            if (index == -1) {
                index = places.size
                val place = PlaceDataBo(
                    placeDto.id,
                    placeDto.name,
                    DateUtils.getApiDateFormatted(placeDto.date),
                    placeDto.todayConfirmed,
                    placeDto.todayDeaths,
                    placeDto.todayNewConfirmed,
                    placeDto.todayNewDeaths,
                    placeDto.todayNewOpenCases,
                    placeDto.todayNewRecovered,
                )
                places.add(index, place)
            } else {
                nearestDate?.let { safeNearestDate ->
                    if (date?.time ?: 0L >= safeNearestDate.time) {
                        nearestDate = date
                        places[index].confirmed = placeDto.todayConfirmed
                        places[index].deaths = placeDto.todayDeaths
                    }
                } ?: run {
                    nearestDate = date
                    places[index].confirmed = placeDto.todayConfirmed
                    places[index].deaths = placeDto.todayDeaths
                }
                places[index].newConfirmed += placeDto.todayNewConfirmed
                places[index].newDeaths += placeDto.todayNewDeaths
                places[index].newOpenCases += placeDto.todayNewOpenCases
                places[index].newRecovered += placeDto.todayNewRecovered
            }
        }
    }
    return DataResponseBo(places, total?.toBo())
}

fun PlaceDataDto.toBo() = CountryDataBo(
    id,
    name,
    DateUtils.getApiDateFormatted(date),
    todayConfirmed,
    todayDeaths,
    todayNewConfirmed,
    todayNewDeaths,
    todayNewOpenCases,
    todayNewRecovered,
)

fun InfoDataDto.toBo() = InfoDataBo(
    DateUtils.getApiDateFormatted(date)
)

fun CovidTotalDataDto.toBo() = CovidTotalDataBo(
    DateUtils.getApiDateFormatted(date),
    name,
    todayConfirmed,
    todayDeaths,
)