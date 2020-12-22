package com.kadirkuruca.weatherapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        btnNetworkControl.setOnClickListener {
            viewModel.gpsCheck()
        }

        swiperCity.setOnRefreshListener {
            viewModel.getLocationAndNearbyCities()
        }

        viewModel.showProgress.observe(this, Observer {
            if(it)
                progress.visibility = VISIBLE
            else
                progress.visibility = GONE
        })

        viewModel.isOnline.observe(this, Observer {
            if(it){
                frameConnection.visibility = GONE
                viewModel.getLocationAndNearbyCities()
            }
            else{
                frameConnection.visibility = VISIBLE
            }
        })

        viewModel.nearbyCities.observe(this, Observer {
            swiperCity.isRefreshing = false
            if(it != null && it.isNotEmpty())
                adapter.setLocationList(it)
            else
                Toast.makeText(this,getString(R.string.cities_listing_error), Toast.LENGTH_SHORT).show()
        })

        viewModel.isGpsEnable.observe(this, Observer {
            if(it)
                viewModel.controlNetwork()
            else{
                buildAlertMessageNoGps()
            }
        })

    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    dialog.dismiss()
                })
            .setNegativeButton("No",
                { dialog, id -> dialog.cancel() })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun init(){
        val actionbar = supportActionBar
        actionbar!!.title = this.getString(R.string.select_city)

        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CityActivityViewModel::class.java)

        adapter = CityAdapter(this)
        recyclerCity!!.layoutManager = layoutManager
        recyclerCity!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            viewModel.gpsCheck()
        },500)
    }
}