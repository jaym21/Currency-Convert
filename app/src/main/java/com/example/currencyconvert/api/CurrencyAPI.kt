package com.example.currencyconvert.api

import com.example.currencyconvert.api.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    @GET("/latest")
    suspend fun getRates(
        @Query("base")
        base: String
    ): Response<CurrencyResponse>
}