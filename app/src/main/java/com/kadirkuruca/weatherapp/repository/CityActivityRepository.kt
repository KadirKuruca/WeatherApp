package com.kadirkuruca.weatherapp.repository

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.kadirkuruca.weatherapp.R
import com.kadirkuruca.weatherapp.network.BASE_URL
import com.kadirkuruca.weatherapp.network.Model.CityLocations
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
class CityActivityRepository(val application: Application) {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val showProgress = MutableLiveData<Boolean>()
    val nearbyCities = MutableLiveData<List<CityLocations>>()
    val isOnline = MutableLiveData<Boolean>()
    val isGpsEnable = MutableLiveData<Boolean>()

    fun controlNetwork() {
        isOnline.value = GlobalFunctions.isOnline(application)
    }

    @SuppressLint("MissingPermission")
    fun getLocationAndNearbyCities() {
        showProgress.value = true
        if (isOnline.value == true) {
            fusedLocationClient =
                com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(
                    application!!
                )

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        getNearbyCities(location)
                    }
                }

        } else
            showProgress.value = false
    }

    fun getNearbyCities(location: Location) {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(WeatherNetwork::class.java)
        var location = "${location.latitude},${location.longitude}"
        service.getCitiesFromLocation(location).enqueue(object : Callback<List<CityLocations>> {
            override fun onFailure(call: Call<List<CityLocations>>, t: Throwable) {
                nearbyCities.value = null
                showProgress.value = false
            }

            override fun onResponse(
                call: Call<List<CityLocations>>,
                response: Response<List<CityLocations>>
            ) {
                nearbyCities.value = response.body()
                showProgress.value = false
            }

        })
    }

    fun gpsCheck() {
        val manager = application.getSystemService(LOCATION_SERVICE) as LocationManager
        isGpsEnable.value = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}


