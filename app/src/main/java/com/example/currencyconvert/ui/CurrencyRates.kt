package com.example.currencyconvert.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyconvert.databinding.ActivityCurrencyRatesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlin.math.round

@AndroidEntryPoint
class CurrencyRates : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyRatesBinding

    //getting viewModel using Dagger-Hilt
    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyRatesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnGetRates.setOnClickListener {
            viewModel.getRatesForCurrency(binding.spCurrencyRates.selectedItem.toString(), binding.tvRatesList)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.rates.collect { event ->
                when(event) {
                    is CurrencyViewModel.RatesEvent.Success -> {
                        binding.tvRatesList.text = "AUD: ${round(event.rates.AUD * 100)/100} \n\n" +
                                "BGN: ${round(event.rates.BGN * 100)/100} \n\n" +
                                "BRL: ${round(event.rates.BRL * 100)/100} \n\n" +
                                "CAD: ${round(event.rates.CAD * 100)/100} \n\n" +
                                "CHF: ${round(event.rates.CHF * 100)/100} \n\n" +
                                "CNY: ${round(event.rates.CNY * 100)/100} \n\n" +
                                "CZK: ${round(event.rates.CZK * 100)/100} \n\n" +
                                "DKK: ${round(event.rates.DKK * 100)/100} \n\n" +
                                "EUR: ${round(event.rates.EUR * 100)/100} \n\n" +
                                "GBP: ${round(event.rates.GBP * 100)/100} \n\n" +
                                "HKD: ${round(event.rates.HKD * 100)/100} \n\n" +
                                "HRK: ${round(event.rates.HRK * 100)/100} \n\n" +
                                "HUF: ${round(event.rates.HUF * 100)/100} \n\n" +
                                "IDR: ${round(event.rates.IDR * 100)/100} \n\n" +
                                "ILS: ${round(event.rates.ILS * 100)/100} \n\n" +
                                "INR: ${round(event.rates.INR * 100)/100} \n\n" +
                                "ISK: ${round(event.rates.ISK * 100)/100} \n\n" +
                                "JPY: ${round(event.rates.JPY * 100)/100} \n\n" +
                                "KRW: ${round(event.rates.KRW * 100)/100} \n\n" +
                                "MXN: ${round(event.rates.MXN * 100)/100} \n\n" +
                                "MYR: ${round(event.rates.MYR * 100)/100} \n\n" +
                                "NOK: ${round(event.rates.NOK * 100)/100} \n\n" +
                                "NZD: ${round(event.rates.NZD * 100)/100} \n\n" +
                                "PHP: ${round(event.rates.PHP * 100)/100} \n\n" +
                                "PLN: ${round(event.rates.PLN * 100)/100} \n\n" +
                                "RON: ${round(event.rates.RON * 100)/100} \n\n" +
                                "RUB: ${round(event.rates.RUB * 100)/100} \n\n" +
                                "SEK: ${round(event.rates.SEK * 100)/100} \n\n" +
                                "SGD: ${round(event.rates.SGD * 100)/100} \n\n" +
                                "THB: ${round(event.rates.THB * 100)/100} \n\n" +
                                "TRY: ${round(event.rates.TRY * 100)/100} \n\n" +
                                "USD: ${round(event.rates.USD * 100)/100} \n\n" +
                                "ZAR: ${round(event.rates.ZAR * 100)/100} \n\n"

                        binding.tvRatesList.movementMethod = ScrollingMovementMethod()
                    }
                }

            }
        }
    }
}