package com.example.domain

import com.example.model.bo.DataResponseBo
import com.example.repository.CovidRepository
import java.util.*


interface GetCountryDataUseCase {

    suspend operator fun invoke(countryId: String, from: Date, to: Date?): DataResponseBo

}

class GetCountryDataUseCaseImpl(
    private val repository: CovidRepository
) : GetCountryDataUseCase {

    override suspend operator fun invoke(countryId: String, from: Date, to: Date?): DataResponseBo =
        repository.getCountryDataByDateRange(countryId, from, to)

}