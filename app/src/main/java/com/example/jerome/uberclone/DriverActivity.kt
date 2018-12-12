package com.example.jerome.uberclone

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class DriverActivity : AppCompatActivity() {

    var requests  =  arrayListOf<String>()

    var locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    var locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)
        setTitle("Nearby Request")

        var requestList = findViewById<ListView>(R.id.request_list)
        requests.add("test")
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,requests)
        requestList.setAdapter(adapter)

    }
}
