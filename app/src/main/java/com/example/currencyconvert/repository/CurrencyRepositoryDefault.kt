package com.example.currencyconvert.repository

import com.example.currencyconvert.api.CurrencyAPI
import com.example.currencyconvert.api.models.CurrencyResponse
import com.example.currencyconvert.util.Resource
import java.lang.Exception
import javax.inject.Inject

//creating the actual repository by implementing interface of repository
//injecting with dagger retrofit(which will be created as a singleton instance with lifecycle same as application) as we need it in repository
class CurrencyRepositoryDefault @Inject constructor(
    private val api: CurrencyAPI //dagger will automatically see the type as CurrencyAPI and inject RetrofitInstance from AppModule
): CurrencyRepository{

    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            //when we get successful response from api
            val response = api.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result!=null) {
                Resource.Success(result)
            } else {
                //if error occurs in api call
                Resource.Error(response.message())
            }
        }catch (e: Exception) {
            //if we get an error in function implementation
            Resource.Error(e.message?: "Error Occurred")
        }
    }
}