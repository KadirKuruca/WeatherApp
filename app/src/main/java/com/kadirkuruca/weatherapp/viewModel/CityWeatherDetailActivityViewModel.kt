package com.kadirkuruca.weatherapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kadirkuruca.weatherapp.repository.CityWeatherDetailActivityRepository

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
class CityWeatherDetailActivityViewModel(application : Application) : AndroidViewModel(application) {

    private val repository = CityWeatherDetailActivityRepository(application)
}