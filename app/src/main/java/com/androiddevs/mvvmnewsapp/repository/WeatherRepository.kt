package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.api.WeatherRetrofitInstance

class WeatherRepository {
    suspend fun getCurrentForecast(cityName: String)
                = WeatherRetrofitInstance.weatherApi.getCurrentForecast(cityName)

    }
