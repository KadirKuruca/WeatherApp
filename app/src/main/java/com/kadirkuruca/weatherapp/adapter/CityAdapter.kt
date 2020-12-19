package com.kadirkuruca.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kadirkuruca.weatherapp.R
import com.kadirkuruca.weatherapp.network.Model.CityLocations
import kotlinx.android.synthetic.main.rv_location_child.view.*

/**
 * Created by Kadir Kuruca on 19.12.2020.
 */
class CityAdapter(private val context : Context) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private var list : List<CityLocations> = ArrayList()

    fun setCityLocations(list : List<CityLocations>){
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
        holder.distance.text = "Distance : ${list[position].distance.toDouble() / 1000} km"
        holder.name.text = list[position].title
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val name = v.tvCityName!!
        val distance = v.tvDistance!!
    }
}