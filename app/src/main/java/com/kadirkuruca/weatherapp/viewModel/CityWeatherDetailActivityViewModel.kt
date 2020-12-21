package com.kadirkuruca.weatherapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kadirkuruca.weatherapp.network.Model.WeatherInfo
import com.kadirkuruca.weatherapp.repository.CityWeatherDetailActivityRepository

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
class CityWeatherDetailActivityViewModel(application : Application) : AndroidViewModel(application) {

    private val repository = CityWeatherDetailActivityRepository(application)
    val weatherInfo : LiveData<WeatherInfo>
    val showProgress : LiveData<Boolean>
    val isOnline : LiveData<Boolean>

    init {
        this.weatherInfo = repository.weatherInfo
        this.showProgress = repository.showProgress
        this.isOnline = repository.isOnline
    }

    fun getWeatherInfo(woeid : Int){
        repository.getWeatherInfo(woeid)
    }

    fun controlNetwork(){
        repository.controlNetwork()
    }
}