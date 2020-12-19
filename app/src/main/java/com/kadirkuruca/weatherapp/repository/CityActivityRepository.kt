package com.kadirkuruca.weatherapp.repository

import android.app.Application
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
class CityActivityRepository(val application: Application) {
    private var locationManager : LocationManager? = null
    val showProgress = MutableLiveData<Boolean>()
    var currentLocation = MutableLiveData<Location>()

    fun changeState(){
        if(showProgress.value != null && showProgress.value!!)
            showProgress.value = false
        else
            showProgress.value = true
    }

    fun getLocation() {
        if(locationManager == null)
            locationManager = application.getSystemService(LOCATION_SERVICE) as LocationManager?

        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
            Log.d("myTag", "Konum isteği gönderildi")
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("myTag", "Konum : ${location.latitude} - ${location.longitude}")
            currentLocation.value = location
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}


