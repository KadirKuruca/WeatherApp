package com.kadirkuruca.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kadirkuruca.weatherapp.R
import com.kadirkuruca.weatherapp.adapter.CityAdapter
import com.kadirkuruca.weatherapp.network.Model.CityLocations
import com.kadirkuruca.weatherapp.viewModel.CityActivityViewModel
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.activity_splash.*

class CityActivity : AppCompatActivity() {

    private lateinit var viewModel : CityActivityViewModel
    private lateinit var adapter : CityAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CityActivityViewModel::class.java)

        viewModel.changeState()
        viewModel.getLocation()

        adapter = CityAdapter(this)
        recyclerCity!!.layoutManager = layoutManager
        recyclerCity!!.adapter = adapter

        viewModel.showProgress.observe(this, Observer {
            if(it)
                progress.visibility = View.VISIBLE
            else
                progress.visibility = View.GONE
        })

        viewModel.nearbyCities.observe(this, Observer {
            adapter.setCityLocations(it)
        })
    }
}