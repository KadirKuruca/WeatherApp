package com.kadirkuruca.weatherapp.network

import com.kadirkuruca.weatherapp.network.Model.CityLocations
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
const val BASE_URL = "https://www.metaweather.com/api/location/"

interface WeatherNetwork {

    @GET("search/")
    fun getCitiesFromLocation(@Query("lattlong", encoded = true) searchString : String) : Call<List<CityLocations>>
}