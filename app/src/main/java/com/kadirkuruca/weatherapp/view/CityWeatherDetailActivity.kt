package com.kadirkuruca.weatherapp.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutSection
import com.bumptech.glide.Glide
import com.kadirkuruca.weatherapp.R
import com.kadirkuruca.weatherapp.utils.GlobalFunctions
import com.kadirkuruca.weatherapp.adapter.WeatherAdapter
import com.kadirkuruca.weatherapp.network.BASE_URL
import com.kadirkuruca.weatherapp.network.Model.WeatherInfo
import com.kadirkuruca.weatherapp.viewModel.CityWeatherDetailActivityViewModel
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.activity_city_weather_detail.*
import kotlinx.android.synthetic.main.activity_city_weather_detail.frameConnection
import kotlinx.android.synthetic.main.connection_error.*
import kotlinx.android.synthetic.main.weather_detail_layout.*

class CityWeatherDetailActivity : AppCompatActivity() {

    var woeid = 0
    private lateinit var viewModel : CityWeatherDetailActivityViewModel
    private lateinit var adapter : WeatherAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_weather_detail)

        init()

        viewModel.controlNetwork()

        btnNetworkControl.setOnClickListener {
            viewModel.controlNetwork()
        }

        if(this.intent != null){
            woeid = this.intent.getIntExtra("woeid",0)
        }

        swiper.setOnRefreshListener {
            viewModel.getWeatherInfo(woeid)
        }

        adapter = WeatherAdapter(this)
        recyclerWeather!!.layoutManager = layoutManager
        recyclerWeather!!.adapter = adapter

        viewModel.isOnline.observe(this, Observer {
            if(it){
                viewModel.getWeatherInfo(woeid)
                frameConnection.visibility = GONE
                layoutWeatherDetail.visibility = VISIBLE
            }
            else{
                frameConnection.visibility = VISIBLE
                layoutWeatherDetail.visibility = GONE
            }
        })

        viewModel.weatherInfo.observe(this, Observer {
            swiper.isRefreshing = false
            setViewValue(it)
        })

        viewModel.showProgress.observe(this, Observer {
            if(it)
                cityWeatherProgress.visibility = VISIBLE
            else
                cityWeatherProgress.visibility = GONE
        })
    }

    private fun init(){
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(
            CityWeatherDetailActivityViewModel::class.java)

        layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
    }

    private fun setViewValue(it : WeatherInfo){

        //Set Current Weather Information
        tvCityName.text = this.intent.getStringExtra("title")
        tvCurrentTemp.text = "${it.consolidatedWeather[0].theTemp.toInt()}°"
        tvMaxMinTemp.text = "${it.consolidatedWeather[0].maxTemp.toInt()}° / ${it.consolidatedWeather[0].minTemp.toInt()}°"
        tvStateName.text = "${it.consolidatedWeather[0].weatherStateName}"

        //Get Weather Image
        var imageUrl = "${BASE_URL}static/img/weather/png/${it.consolidatedWeather[0].weatherStateAbbr}.png"
        Glide.with(this).load(imageUrl).into(ivCurrentWeatherImage)

        //Set Consolidate Adapter
        val consolidatedList = it.consolidatedWeather.subList(1,it.consolidatedWeather.size)
        adapter.setWeatherList(consolidatedList)

        //Set DonutView
        setDonutView(it.consolidatedWeather[0].humidity)

        //Wind Info
        tvWindSpeedValue.text = "${it.consolidatedWeather[0].windSpeed.toInt()} mph"
        tvWindDirectionValue.text = "${GlobalFunctions.getWindDirectionName(it.consolidatedWeather[0].windDirectionCompass)}"

        //Sunrise - Sunset
        tvSunriseValue.text = "${GlobalFunctions.getHourFromDate(it.sunRise)}"
        tvSunsetValue.text = "${GlobalFunctions.getHourFromDate(it.sunSet)}"
    }

    private fun setDonutView(humidity : Int){
        val section1 = DonutSection(
            name = "section_1",
            color = Color.WHITE,
            amount = humidity.toFloat()
        )

        tvHumidityValue.text = "%${humidity}"
        donut_view.cap = 100f
        donut_view.submitData(listOf(section1))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}