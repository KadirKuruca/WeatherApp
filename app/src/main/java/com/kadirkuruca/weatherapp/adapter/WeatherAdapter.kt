package com.kadirkuruca.weatherapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kadirkuruca.weatherapp.R
import com.kadirkuruca.weatherapp.utils.GlobalFunctions
import com.kadirkuruca.weatherapp.network.BASE_URL
import com.kadirkuruca.weatherapp.network.Model.ConsolidatedWeather
import kotlinx.android.synthetic.main.rv_weather_child.view.*

/**
 * Created by Kadir Kuruca on 20.12.2020.
 */
class WeatherAdapter(private val context : Context) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var list : List<ConsolidatedWeather> = ArrayList()

    fun setWeatherList(list : List<ConsolidatedWeather>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rv_weather_child,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = GlobalFunctions.getFormattedDate(list[position].applicableDate)
        holder.temp.text = "${list[position].maxTemp.toInt()}° / ${list[position].minTemp.toInt()}°"
        var imageUrl = "${BASE_URL}static/img/weather/png/${list[position].weatherStateAbbr}.png"
        Log.d("myTagImage",imageUrl)
        Glide.with(context).load(imageUrl).into(holder.image)
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val date = v.tvChildDate!!
        val temp = v.tvChildTemp!!
        val image = v.ivWeatherImage!!
        val rootView = v.rootView2!!
    }
}