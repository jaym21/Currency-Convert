package com.example.currencyconvert.ui

import android.provider.Telephony
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconvert.api.models.Rates
import com.example.currencyconvert.repository.CurrencyRepository
import com.example.currencyconvert.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class CurrencyViewModel @ViewModelInject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {



    //creating events for viewModel functions response events
    sealed class CurrencyEvents {
        class Success(val resultAmount: String): CurrencyEvents()
        class Failure(val errorMessage: String): CurrencyEvents()
        object Loading: CurrencyEvents() //for loading state to display loading sign(progressBar)
        object InitialState: CurrencyEvents() //for initial state
    }

    sealed class RatesEvent {
        class Success(val rates: Rates): RatesEvent()
        class Failure(val errorMessage: String): RatesEvent()
        object InitialState: RatesEvent()
    }

    //making the conversion value LiveData
    private val _conversion = MutableStateFlow<CurrencyEvents>(CurrencyEvents.InitialState)
    val conversion: StateFlow<CurrencyEvents> = _conversion

    //making rates value LiveData
    private val _rates = MutableStateFlow<RatesEvent>(RatesEvent.InitialState)
    val rates: StateFlow<RatesEvent> = _rates



    //this function will request for rates and make the conversion according to the currency
    fun convertAmount(amount: String, fromCurrency: String, toCurrency: String) {

        val enteredAmount = amount.toFloatOrNull() //this will convert the amount to float, but if there is an invalid input given like letters then it will be converted to null
        //checking if the enteredAmount is not valid input
        if (enteredAmount == null) {
            //if it is not valid input then calling the Failure event and returning an error message
            _conversion.value = CurrencyEvents.Failure("Invalid Input")
            return
        }

        //if the enteredAmount was valid input
        viewModelScope.launch(Dispatchers.IO) {
            //while making a api call in background thread through coroutine we display loading event
            _conversion.value = CurrencyEvents.Loading
            when (val ratesResponse = currencyRepository.getRates(fromCurrency)) {
                //if we get a successful response on getRates function from repository(which can be checked with the help of wrapper class Resource)
                is Resource.Success -> {
                    //getting the rates
                    val rates = ratesResponse.data!!.rates
                    val rateOfToCurrency = getRateForCurrency(toCurrency, rates)
                    //checking if we get null response from api for currency rate
                    if (rateOfToCurrency == null) {
                        _conversion.value = CurrencyEvents.Failure("Error while getting rate for required currency")
                    }else {
                        //if we get valid rate response
                        //calculating the converted currency(required currency) value by original and required currency rate
                        val convertedCurrency = round(enteredAmount * rateOfToCurrency * 100)/100 //multiplication and division of 100 limits the result to 2 decimal places
                        _conversion.value = CurrencyEvents.Success("$fromCurrency $enteredAmount ⇋ $convertedCurrency $toCurrency")
                    }

                }
                is Resource.Error -> _conversion.value = CurrencyEvents.Failure(ratesResponse.responseMessage!!)
            }
        }
    }

    //this function will get rates by passing the countryCode selected from spinner
    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.CAD
        "HKD" -> rates.HKD
        "ISK" -> rates.ISK
        "EUR" -> rates.EUR
        "PHP" -> rates.PHP
        "DKK" -> rates.DKK
        "HUF" -> rates.HUF
        "CZK" -> rates.CZK
        "AUD" -> rates.AUD
        "RON" -> rates.RON
        "SEK" -> rates.SEK
        "IDR" -> rates.IDR
        "INR" -> rates.INR
        "BRL" -> rates.BRL
        "RUB" -> rates.RUB
        "HRK" -> rates.HRK
        "JPY" -> rates.JPY
        "THB" -> rates.THB
        "CHF" -> rates.CHF
        "SGD" -> rates.SGD
        "PLN" -> rates.PLN
        "BGN" -> rates.BGN
        "CNY" -> rates.CNY
        "NOK" -> rates.NOK
        "NZD" -> rates.NZD
        "ZAR" -> rates.ZAR
        "USD" -> rates.USD
        "MXN" -> rates.MXN
        "ILS" -> rates.ILS
        "GBP" -> rates.GBP
        "KRW" -> rates.KRW
        "MYR" -> rates.MYR
        else -> null
    }

    //to get Rates for Currency Rates activity
    fun getRatesForCurrency(base: String, tvRatesList: TextView) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val ratesResponse = currencyRepository.getRates(base)) {
                is Resource.Success -> {
                    //getting the rates
                    val rates = ratesResponse.data?.rates
                    if (rates == null) {
                        _rates.value = RatesEvent.Failure("Error occurred in rates response from api")
                    }else{
                        _rates.value = RatesEvent.Success(rates)
                    }
                }
                is Resource.Error -> {
                    _rates.value = RatesEvent.Failure(ratesResponse.responseMessage!!)
                }
            }
        }
    }

}