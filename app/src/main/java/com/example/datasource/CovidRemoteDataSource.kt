package com.example.datasource

import com.example.model.bo.DataResponseBo
import java.util.*


interface CovidRemoteDataSource {
    suspend fun getWorldDataByDateRange(from: Date, to: Date): DataResponseBo
    suspend fun getCountryDataByDateRange(countryId: String, from: Date, to: Date): DataResponseBo
}