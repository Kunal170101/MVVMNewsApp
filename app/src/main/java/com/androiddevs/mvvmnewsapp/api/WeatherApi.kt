package com.androiddevs.mvvmnewsapp.api

import com.androiddevs.mvvmnewsapp.models.CurrentWeatherResponse
import com.androiddevs.mvvmnewsapp.util.Constants.Companion.WEATHER_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/current.json")
    suspend fun getCurrentForecast(
        @Query("q")
        cityName: String = "New Delhi",
        @Query("key")
        apiKey: String = WEATHER_API_KEY
    ): Response<CurrentWeatherResponse>
}