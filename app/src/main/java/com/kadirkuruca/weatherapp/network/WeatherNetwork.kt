package com.kadirkuruca.weatherapp.network

import com.kadirkuruca.weatherapp.network.Model.CityLocations
import com.kadirkuruca.weatherapp.network.Model.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
const val BASE_URL = "https://www.metaweather.com/"

interface WeatherNetwork {

    @GET("api/location/search/")
    fun getCitiesFromLocation(@Query("lattlong", encoded = true) searchString : String) : Call<List<CityLocations>>

    @GET("api/location/{woeid}")
    fun getLocationWeather(@Path("woeid") woeid: Int) : Call<WeatherInfo>
}