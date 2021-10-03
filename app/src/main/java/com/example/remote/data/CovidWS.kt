package com.example.remote.data

import com.example.remote.data.dto.DataResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CovidWS {

    @GET("api")
    suspend fun getData(
        @Query("date_from") from: String,
        @Query("date_to") to: String,
    ): DataResponseDto

    @GET("api/country/:country")
    suspend fun getCountryData(
        @Path("country") countryId: String,
        @Query("date_from") from: String,
        @Query("date_to") to: String,
    ): DataResponseDto
}