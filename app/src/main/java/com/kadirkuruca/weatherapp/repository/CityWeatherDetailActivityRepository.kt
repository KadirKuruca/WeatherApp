package com.kadirkuruca.weatherapp.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.kadirkuruca.weatherapp.R
import com.kadirkuruca.weatherapp.network.BASE_URL
import com.kadirkuruca.weatherapp.network.Model.CityLocations
import com.kadirkuruca.weatherapp.network.Model.WeatherInfo
import com.kadirkuruca.weatherapp.network.WeatherNetwork
import com.kadirkuruca.weatherapp.utils.GlobalFunctions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
class CityWeatherDetailActivityRepository(val application: Application) {

    val showProgress = MutableLiveData<Boolean>()
    val weatherInfo = MutableLiveData<WeatherInfo>()
    val isOnline = MutableLiveData<Boolean>()

    fun controlNetwork() {
        isOnline.value = GlobalFunctions.isOnline(application)
    }

    fun getWeatherInfo(woeid: Int) {
        if(isOnline.value == true){
            showProgress.value = true
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

            val service = retrofit.create(WeatherNetwork::class.java)
            service.getLocationWeather(woeid).enqueue(object : Callback<WeatherInfo> {
                override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                    showProgress.value = false
                    Toast.makeText(application,application.getString(R.string.cities_listing_error), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<WeatherInfo>,
                    response: Response<WeatherInfo>
                )
                {
                    weatherInfo.value = response.body()
                    showProgress.value = false
                }
            })
        }
        else
            showProgress.value = false
    }
}