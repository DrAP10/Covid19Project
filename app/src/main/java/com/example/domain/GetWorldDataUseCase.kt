package com.example.domain

import com.example.model.bo.DataResponseBo
import com.example.repository.CovidRepository
import java.util.*


interface GetWorldDataUseCase {

    suspend operator fun invoke(from: Date, to: Date?): DataResponseBo

}

class GetWorldDataUseCaseImpl(
    private val repository: CovidRepository
) : GetWorldDataUseCase {

    override suspend operator fun invoke(from: Date, to: Date?): DataResponseBo =
        repository.getWorldDataByDateRange(from, to)

}