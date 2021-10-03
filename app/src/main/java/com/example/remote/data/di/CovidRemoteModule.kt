package com.example.remote.data.di

import com.example.datasource.CovidRemoteDataSource
import com.example.remote.data.CovidRemoteDataSourceImpl
import com.example.remote.data.CovidWS
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class CovidRemoteModule {

    @Singleton
    @Provides
    fun provideCovidApi(retrofit: Retrofit): CovidWS {
        return retrofit.create(CovidWS::class.java)
    }

    @Provides
    fun covidRemoteDataSourceProvider(covidWS: CovidWS) =
        CovidRemoteDataSourceImpl(covidWS) as CovidRemoteDataSource

}