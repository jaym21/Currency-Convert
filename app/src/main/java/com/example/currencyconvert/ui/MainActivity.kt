package com.example.currencyconvert.ui

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.currencyconvert.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //getting viewModel using Dagger-Hilt
    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener {

            //calling the convertAmount function from viewModel when Convert button is clicked
            viewModel.convertAmount(
                binding.etAmount.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString())
        }

        lifecycleScope.launchWhenStarted {
            //getting all the events from viewModel
            viewModel.conversion.collect { event ->
                when(event) {
                    is CurrencyViewModel.CurrencyEvents.Success -> {
                        //when we get a success event
                        binding.progressBar.isVisible = false
                        binding.tvFromCurrencyResult.setTextColor(Color.WHITE)
                        binding.tvFromCurrencyResult.setTextSize(22F)
                        binding.tvFromCurrencyResult.text = "${binding.etAmount.text.toString()} ${binding.spFromCurrency.selectedItem.toString()}"
                        binding.tvToCurrencyResult.setTextColor(Color.WHITE)
                        binding.tvToCurrencyResult.setTextSize(22F)
                        binding.tvToCurrencyResult.text = event.resultAmount

                        binding.cvResult.visibility = View.VISIBLE
                    }
                    is CurrencyViewModel.CurrencyEvents.Failure -> {
                        //when we get a failure event
                        binding.progressBar.isVisible = false
                        binding.tvFromCurrencyResult.setTextColor(Color.RED)
                        binding.tvFromCurrencyResult.text = event.errorMessage
                    }
                    is CurrencyViewModel.CurrencyEvents.Loading -> {
                        //when we get a loading event then we will display progressBar(loading sign)
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }

        //when see rates btn is clicked
        binding.btnSeeRates.setOnClickListener {
            startActivity(Intent(this, CurrencyRates::class.java))
        }
    }
}