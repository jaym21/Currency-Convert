package com.example.currencyconvert.dagger

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//an application class with @HiltAndroidApp annotation is required
@HiltAndroidApp
class CurrencyApplication: Application() {
}