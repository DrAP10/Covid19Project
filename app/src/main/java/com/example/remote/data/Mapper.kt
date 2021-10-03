package com.example.remote.data

import com.example.DateUtils
import com.example.model.bo.*
import com.example.remote.data.dto.*


fun DataResponseDto.toBo() = DataResponseBo(
    dates.mapValues { it.value.toBo() },
    total.toBo(),
)

fun DataDto.toBo() = DataBo(
    countries.mapValues { it.value.toBo() },
    info.toBo(),
)

fun CountryDataDto.toBo() = CountryDataBo(
    id,
    name,
    DateUtils.getApiDateFormatted(date),
    todayConfirmed,
    todayDeath,
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
    todayNewDeath,
    todayNewRecovered,
)