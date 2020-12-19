package com.kadirkuruca.weatherapp.viewModel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kadirkuruca.weatherapp.repository.CityActivityRepository

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
class CityActivityViewModel(application : Application) : AndroidViewModel(application) {

    private val repository = CityActivityRepository(application)
    val showProgress : LiveData<Boolean>
    val currentLocation : LiveData<Location>

    init {
        this.showProgress = repository.showProgress
        this.currentLocation = repository.currentLocation
    }

    fun changeState(){
        repository.changeState()
    }

    fun getLocation(){
        repository.getLocation()
    }
}