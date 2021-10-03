package com.example.repository.di

import com.example.datasource.CovidRemoteDataSource
import com.example.repository.CovidRepository
import com.example.repository.CovidRepositoryImpl
import dagger.Module
import dagger.Provides


@Module
class CovidRepositoryModule {

    @Provides
    fun covidRepositoryProvider(remote: CovidRemoteDataSource) =
        CovidRepositoryImpl(remote) as CovidRepository

}