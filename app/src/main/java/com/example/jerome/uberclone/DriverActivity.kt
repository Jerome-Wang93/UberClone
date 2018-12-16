package com.example.jerome.uberclone

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class DriverActivity : AppCompatActivity() {

    var requests  =  arrayListOf<String>()
    lateinit var adapter : ArrayAdapter<*>


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

    fun updateList() {



    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    requests.add("test")
                    Log.i("jian","add")
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)
        setTitle("Nearby Request")
        requests.add("These the nearby requests:")
        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,requests)
        var requestList = findViewById<ListView>(R.id.request_list)
        requestList.setAdapter(adapter)


        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
        }else{
            updateList()
        }




    }
}
