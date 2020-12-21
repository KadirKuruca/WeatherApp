package com.kadirkuruca.weatherapp.repository

import android.app.Application
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
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
    private var locationManager: LocationManager? = null
    val showProgress = MutableLiveData<Boolean>()
    val nearbyCities = MutableLiveData<List<CityLocations>>()
    val isOnline = MutableLiveData<Boolean>()

    fun controlNetwork() {
        isOnline.value = GlobalFunctions.isOnline(application)
    }

    fun getLocation() {
        showProgress.value = true
        if (isOnline.value == true) {
            if (locationManager == null)
                locationManager = application.getSystemService(LOCATION_SERVICE) as LocationManager?

            try {
                // Request location updates
                locationManager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
                Log.d("myTag", "Konum isteği gönderildi")
            } catch (ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available")
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
                showProgress.value = false
                Toast.makeText(
                    application,
                    "Şehirler getirilirken hata oluştu!",
                    Toast.LENGTH_SHORT
                ).show()
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

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("myTag", "Konum : ${location.latitude} - ${location.longitude}")
            getNearbyCities(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}


