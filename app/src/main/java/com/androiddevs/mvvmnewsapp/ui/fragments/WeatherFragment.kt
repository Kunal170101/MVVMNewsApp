package com.androiddevs.mvvmnewsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.models.Condition
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.WeatherViewModel
import com.androiddevs.mvvmnewsapp.util.Resource
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_weather_current.*

class WeatherFragment : Fragment(R.layout.fragment_weather_current) {

    lateinit var viewModel: WeatherViewModel
    val TAG = "WeatherFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).weatherViewModel


        viewModel.currentWeather.observe(viewLifecycleOwner, Observer {response->
            if(response==null) return@Observer
            when(response) {
                is Resource.Success -> {
                    response.data?.let { weatherResponse ->
                        updateTemperatures(weatherResponse.current.temp_c, weatherResponse.current.feelslike_c)
                        updateCondition(weatherResponse.current.condition)
                        updatePrecipitation(weatherResponse.current.precip_mm)
                        updateWind(weatherResponse.current.wind_kph)
                        updateVisibility(weatherResponse.current.vis_km)
                        updateHumidity(weatherResponse.current.humidity)
                        updateUv(weatherResponse.current.uv)
                        updatePressure(weatherResponse.current.pressure_in)
                        updateLocation(weatherResponse.location.name)
                        updateLocalTime(weatherResponse.location.localtime)

                        Glide.with(this@WeatherFragment)
                            .load("https:${weatherResponse.current.condition.icon}")
                            .into(imageView_condition_icon)
                    }
                }
                is Resource.Error -> {

                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {

                }
            }
        })


    }




    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = " °C"
        textView_temperature.text = "$temperature$unitAbbreviation"
        feels_like.text = "Feels like: $feelsLike$unitAbbreviation"

    }

    private fun updateCondition(condition: Condition) {
        textView_condition.text = condition.text
    }

    private fun updateHumidity(humid: Int) {

        humidity.text = "$humid%"
    }

    @SuppressLint("SetTextI18n")
    private fun updateWind(windSpeed: Double) {

        wind.text = "$windSpeed km/h"
    }

    private fun updateUv(uv: Double) {

        textview_uv.text = "$uv"
    }

    private fun updatePressure(pressure: Double) {

        tv_pressure.text = "$pressure in"
    }

    private fun updatePrecipitation(precipitation: Double) {

        tv_precipitation.text = "$precipitation mm"
    }

    private fun updateVisibility(visibility: Double) {

        tv_visibility.text = "$visibility km"
    }

    private fun updateLocation(location: String) {

        address.text = location
    }

    private fun updateLocalTime(lastUpdated: String){
        updated_at.text = lastUpdated
    }


}