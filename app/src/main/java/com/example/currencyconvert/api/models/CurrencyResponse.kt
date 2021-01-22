package com.example.currencyconvert.api.models

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Rates
)