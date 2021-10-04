package com.example.domain.di

import com.example.domain.GetCountryDataUseCase
import com.example.domain.GetCountryDataUseCaseImpl
import com.example.domain.GetWorldDataUseCase
import com.example.domain.GetWorldDataUseCaseImpl
import com.example.repository.CovidRepository
import dagger.Module
import dagger.Provides


@Module
class CovidUseCaseModule {

    @Provides
    fun getWorldDataUseCaseProvider(repository: CovidRepository) =
        GetWorldDataUseCaseImpl(repository) as GetWorldDataUseCase

    @Provides
    fun getCountryDataUseCaseProvider(repository: CovidRepository) =
        GetCountryDataUseCaseImpl(repository) as GetCountryDataUseCase

}