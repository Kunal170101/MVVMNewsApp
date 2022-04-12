package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.models.CurrentWeatherResponse

import com.androiddevs.mvvmnewsapp.repository.WeatherRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(
    val weatherRepository: WeatherRepository
) : ViewModel() {

    val currentWeather: MutableLiveData<Resource<CurrentWeatherResponse>> = MutableLiveData()
    var currentWeatherResponse: CurrentWeatherResponse? = null

    init {
        getCurrentForecast("New Delhi")
    }

    fun getCurrentForecast(cityName: String) = viewModelScope.launch {
        currentWeather.postValue(Resource.Loading())
        val response = weatherRepository.getCurrentForecast(cityName)
        currentWeather.postValue(handleCurrentWeatherResponse(response))
    }

    private fun handleCurrentWeatherResponse(response: Response<CurrentWeatherResponse>) : Resource<CurrentWeatherResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(currentWeatherResponse == null) {
                    currentWeatherResponse = resultResponse
                }
                return Resource.Success(currentWeatherResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}