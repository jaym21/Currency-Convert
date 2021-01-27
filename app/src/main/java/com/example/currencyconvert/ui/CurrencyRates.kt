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
                        binding.tvRatesList.text = "AUD(Australian Dollar): ${round(event.rates.AUD * 100)/100} \n\n" +
                                "BGN(Bulgarian Lev): ${round(event.rates.BGN * 100)/100} \n\n" +
                                "BRL(Brazilian Real): ${round(event.rates.BRL * 100)/100} \n\n" +
                                "CAD(Canadian Dollar): ${round(event.rates.CAD * 100)/100} \n\n" +
                                "CHF(Swiss Franc): ${round(event.rates.CHF * 100)/100} \n\n" +
                                "CNY(Chinese Yuan): ${round(event.rates.CNY * 100)/100} \n\n" +
                                "CZK(Czech Koruna): ${round(event.rates.CZK * 100)/100} \n\n" +
                                "DKK(Danish Krone): ${round(event.rates.DKK * 100)/100} \n\n" +
                                "EUR(Euro): ${round(event.rates.EUR * 100)/100} \n\n" +
                                "GBP(Pound Sterling): ${round(event.rates.GBP * 100)/100} \n\n" +
                                "HKD(Hong Kong Dollar): ${round(event.rates.HKD * 100)/100} \n\n" +
                                "HRK(Croatian Kuna): ${round(event.rates.HRK * 100)/100} \n\n" +
                                "HUF(Hungarian Forint): ${round(event.rates.HUF * 100)/100} \n\n" +
                                "IDR(Indonesian Rupiah): ${round(event.rates.IDR * 100)/100} \n\n" +
                                "ILS(New Israeli Sheqel): ${round(event.rates.ILS * 100)/100} \n\n" +
                                "INR(Indian Rupee): ${round(event.rates.INR * 100)/100} \n\n" +
                                "ISK(Iceland Krona): ${round(event.rates.ISK * 100)/100} \n\n" +
                                "JPY(Japanese Yen): ${round(event.rates.JPY * 100)/100} \n\n" +
                                "KRW(Korean Won): ${round(event.rates.KRW * 100)/100} \n\n" +
                                "MXN(Mexican Peso): ${round(event.rates.MXN * 100)/100} \n\n" +
                                "MYR(Malaysian Ringgit): ${round(event.rates.MYR * 100)/100} \n\n" +
                                "NOK(Norwegian Krone): ${round(event.rates.NOK * 100)/100} \n\n" +
                                "NZD(New Zealand Dollar): ${round(event.rates.NZD * 100)/100} \n\n" +
                                "PHP(Philippine Peso): ${round(event.rates.PHP * 100)/100} \n\n" +
                                "PLN(Poland Zloty): ${round(event.rates.PLN * 100)/100} \n\n" +
                                "RON(Romanian Leu): ${round(event.rates.RON * 100)/100} \n\n" +
                                "RUB(Russian Ruble): ${round(event.rates.RUB * 100)/100} \n\n" +
                                "SEK(Swedish Krona): ${round(event.rates.SEK * 100)/100} \n\n" +
                                "SGD(Singapore Dollar): ${round(event.rates.SGD * 100)/100} \n\n" +
                                "THB(Thailand Baht): ${round(event.rates.THB * 100)/100} \n\n" +
                                "TRY(Turkish Lira): ${round(event.rates.TRY * 100)/100} \n\n" +
                                "USD(US Dollar): ${round(event.rates.USD * 100)/100} \n\n" +
                                "ZAR(Rand): ${round(event.rates.ZAR * 100)/100} \n\n"

                        binding.tvRatesList.movementMethod = ScrollingMovementMethod()
                    }
                }

            }
        }
    }
}