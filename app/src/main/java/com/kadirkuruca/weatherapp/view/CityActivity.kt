package com.kadirkuruca.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kadirkuruca.weatherapp.R
import com.kadirkuruca.weatherapp.viewModel.CityActivityViewModel
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.activity_splash.*

class CityActivity : AppCompatActivity() {

    private lateinit var viewModel : CityActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CityActivityViewModel::class.java)

        btnStop.setOnClickListener{
            viewModel.changeState()
            viewModel.getLocation()
        }

        viewModel.showProgress.observe(this, Observer {
            if(it)
                progress.visibility = View.VISIBLE
            else
                progress.visibility = View.GONE
        })

        viewModel.currentLocation.observe(this, Observer {
            viewModel.changeState()
            Toast.makeText(this,"Location : ${it.latitude} - ${it.longitude}",Toast.LENGTH_SHORT).show()
        })
    }
}