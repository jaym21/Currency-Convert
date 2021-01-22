package com.example.currencyconvert.repository

import com.example.currencyconvert.api.models.CurrencyResponse
import com.example.currencyconvert.util.Resource

//making an interface for testing purpose
//for testing we create a fakeRepositopry other than our actual repository and implement this interface functions and make api calls for testing
interface CurrencyRepository {

    //with help of wrapper class Resource we can handle of there is error from api call
    suspend fun getRates(base: String): Resource<CurrencyResponse>
}