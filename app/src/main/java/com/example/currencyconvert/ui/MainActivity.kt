package com.example.currencyconvert.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyconvert.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
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
                binding.etAmount.toString(),
                binding.spFromCurrency.toString(),
                binding.spToCurrency.toString())
        }

        lifecycleScope.launchWhenStarted {
            //getting all the events from viewModel
            viewModel.conversion.javaClass
        }
    }
}