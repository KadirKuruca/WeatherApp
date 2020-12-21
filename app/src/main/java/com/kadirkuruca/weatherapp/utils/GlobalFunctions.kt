package com.kadirkuruca.weatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Kadir Kuruca on 20.12.2020.
 */
class GlobalFunctions {

    companion object {
        fun getHourFromDate(date: String): String {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("HH:mm")
            val formattedDate = formatter.format(parser.parse(date))
            return formattedDate
        }

        fun getFormattedDate(date: String): String {
            val parser = SimpleDateFormat("yyyy-MM-dd")
            val formatter = SimpleDateFormat("dd MMM EEE", Locale.UK)
            val formattedDate = formatter.format(parser.parse(date))
            return formattedDate
        }

        fun getWindDirectionName(compass: String): String {
            var name = ""
            when (compass) {
                "N" -> name = "North"
                "NNE" -> name = "North-North-East"
                "NE" -> name = "North-East"
                "ENE" -> name = "East-North-East"
                "E" -> name = "East"
                "ESE" -> name = "East-South-East"
                "SE" -> name = "South-East"
                "SSE" -> name = "South-South-East"
                "S" -> name = "South"
                "SSW" -> name = "South-South-West"
                "SW" -> name = "South-West"
                "WSW" -> name = "West-South-West"
                "W" -> name = "West"
                "WNW" -> name = "West-North-West"
                "NW" -> name = "North-West"
                "NNW" -> name = "North-North-West"
            }
            return name
        }

        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }
    }
}