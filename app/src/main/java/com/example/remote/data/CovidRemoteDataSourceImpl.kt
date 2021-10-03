package com.example.remote.data

import com.example.DateUtils
import com.example.datasource.CovidRemoteDataSource
import java.util.*


class CovidRemoteDataSourceImpl(
    private val covidWS: CovidWS
) : CovidRemoteDataSource {

    override suspend fun getWorldDataByDateRange(from: Date, to: Date) =
        covidWS.getData(
            DateUtils.getApiDateStringFormatted(from),
            DateUtils.getApiDateStringFormatted(to),
        ).toBo()

    override suspend fun getCountryDataByDateRange(countryId: String, from: Date, to: Date) =
        covidWS.getCountryData(
            countryId,
            DateUtils.getApiDateStringFormatted(from),
            DateUtils.getApiDateStringFormatted(to),
        ).toBo()
}