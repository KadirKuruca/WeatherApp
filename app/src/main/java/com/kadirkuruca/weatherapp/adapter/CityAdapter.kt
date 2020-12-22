package com.kadirkuruca.weatherapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kadirkuruca.weatherapp.R
import com.kadirkuruca.weatherapp.network.Model.CityLocations
import com.kadirkuruca.weatherapp.view.CityWeatherDetailActivity
import kotlinx.android.synthetic.main.rv_location_child.view.*

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
class CityAdapter(private val context : Context) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private var list : List<CityLocations> = ArrayList()

    fun setLocationList(list : List<CityLocations>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rv_location_child,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val distance = String.format("%.1f",list[position].distance.toDouble() / 1000)
        holder.distance.text = "Distance : ${distance} km"
        holder.name.text = list[position].title

        holder.rootView.setOnClickListener {
            val intent = Intent(context, CityWeatherDetailActivity::class.java)
            intent.putExtra("title",list[position].title)
            intent.putExtra("woeid",list[position].woeid)
            context.startActivity(intent)
        }
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val name = v.tvCityName!!
        val distance = v.tvDistance!!
        val rootView = v.rootView!!
    }
}