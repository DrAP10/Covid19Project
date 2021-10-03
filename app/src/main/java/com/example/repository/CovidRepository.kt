package com.example.repository

import com.example.datasource.CovidRemoteDataSource
import com.example.model.bo.DataResponseBo
import java.util.*

interface CovidRepository {
    suspend fun getWorldDataByDateRange(from: Date, to: Date? = null): DataResponseBo
    suspend fun getCountryDataByDateRange(countryId: String, from: Date, to: Date? = null): DataResponseBo
}

internal class CovidRepositoryImpl(
    private val remote: CovidRemoteDataSource
) : CovidRepository {

    override suspend fun getWorldDataByDateRange(from: Date, to: Date?): DataResponseBo =
        remote.getWorldDataByDateRange(from, to ?: from)

    override suspend fun getCountryDataByDateRange(countryId: String, from: Date, to: Date?): DataResponseBo =
        remote.getCountryDataByDateRange(countryId, from, to ?: from)

}