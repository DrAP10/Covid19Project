package com.example.remote.data

import com.example.DateUtils
import com.example.model.bo.*
import com.example.remote.data.dto.*
import java.util.*


fun DataResponseDto.toBo(): DataResponseBo {
    val places = mutableListOf<PlaceDataBo>()
    var nearestDate: Date? = null
    dates.forEach {
        it.value.countries.forEach { placeMap ->
            val placeDto = placeMap.value
            val date = DateUtils.getApiDateFormatted(placeDto.date)
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
                    placeDto.regions.map { regionDataDto ->  regionDataDto.toBo() } as MutableList<RegionDataBo>
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

                val regions = places[index].regions
                placeDto.regions.forEach { regionDto ->
                    val regionIndex = regions.indexOfFirst { item -> item.id == placeDto.id }
                    if (regionIndex == -1) {
                        regions.add(regions.size, regionDto.toBo())
                    } else {
                        nearestDate?.let { safeNearestDate ->
                            if (date?.time ?: 0L >= safeNearestDate.time) {
                                nearestDate = date
                                regions[regionIndex].confirmed = regionDto.todayConfirmed
                                regions[regionIndex].deaths = regionDto.todayDeaths
                            }
                        } ?: run {
                            nearestDate = date
                            regions[regionIndex].confirmed = regionDto.todayConfirmed
                            regions[regionIndex].deaths = regionDto.todayDeaths
                        }
                        regions[regionIndex].newConfirmed += regionDto.todayNewConfirmed
                        regions[regionIndex].newDeaths += regionDto.todayNewDeaths
                        regions[regionIndex].newOpenCases += regionDto.todayNewOpenCases
                        regions[regionIndex].newRecovered += regionDto.todayNewRecovered
                    }
                }


            }
        }
    }
    return DataResponseBo(places, total?.toBo())
}

fun RegionDataDto.toBo() = RegionDataBo(
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