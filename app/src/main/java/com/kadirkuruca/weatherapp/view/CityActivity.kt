package com.kadirkuruca.weatherapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kadirkuruca.weatherapp.R
import com.kadirkuruca.weatherapp.adapter.CityAdapter
import com.kadirkuruca.weatherapp.viewModel.CityActivityViewModel
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.connection_error.*


class CityActivity : AppCompatActivity() {

    private lateinit var viewModel : CityActivityViewModel
    private lateinit var adapter : CityAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        init()

        viewModel.controlNetwork()

        btnNetworkControl.setOnClickListener {
            viewModel.controlNetwork()
        }

        viewModel.showProgress.observe(this, Observer {
            if(it)
                progress.visibility = VISIBLE
            else
                progress.visibility = GONE
        })

        viewModel.isOnline.observe(this, Observer {
            if(it){
                viewModel.getLocation()
                frameConnection.visibility = GONE
            }
            else{
                frameConnection.visibility = VISIBLE
            }
        })

        viewModel.nearbyCities.observe(this, Observer {
            adapter.setLocationList(it)
        })
    }

    private fun init(){
        val actionbar = supportActionBar
        actionbar!!.title = "Şehir Seçiniz"

        layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CityActivityViewModel::class.java)

        adapter = CityAdapter(this)
        recyclerCity!!.layoutManager = layoutManager
        recyclerCity!!.adapter = adapter
    }
}