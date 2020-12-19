package com.kadirkuruca.weatherapp.viewModel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kadirkuruca.weatherapp.network.Model.CityLocations
import com.kadirkuruca.weatherapp.repository.CityActivityRepository

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
class CityActivityViewModel(application : Application) : AndroidViewModel(application) {

    private val repository = CityActivityRepository(application)
    val showProgress : LiveData<Boolean>
    val nearbyCities : LiveData<List<CityLocations>>

    init {
        this.showProgress = repository.showProgress
        this.nearbyCities = repository.nearbyCities
    }

    fun changeState(){
        repository.changeState()
    }

    fun getLocation(){
        repository.getLocation()
    }

    fun getNearbyCities(location: Location){
        repository.getNearbyCities(location)
    }
}