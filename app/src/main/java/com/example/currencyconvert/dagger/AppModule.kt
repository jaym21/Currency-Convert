package com.example.currencyconvert.dagger

import com.example.currencyconvert.api.CurrencyAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

//base Url for api call
private const val BASE_URL = "https://api.exchangeratesapi.io"

//this object will have the functions that create the dependencies we want to inject
@Module
@InstallIn(ApplicationComponent::class) //i.e the dependencies well live as long as application lifecycle
object AppModule {

    //creating Retrofit2 api as singleton
    @Singleton
    @Provides
    fun provideRetrofitCurrencyApi(): CurrencyAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyAPI::class.java)
}