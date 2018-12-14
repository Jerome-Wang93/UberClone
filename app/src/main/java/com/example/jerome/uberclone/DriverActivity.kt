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

    var locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    var locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            updateList(location)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }
    }

    fun updateList(location : Location){
        requests.clear()
        var query = ParseQuery<ParseObject>("Request")
        var geopoint = ParseGeoPoint(location.latitude,location.longitude)
        query.whereNear("Location",geopoint)
        query.setLimit(10)
        query.findInBackground { objects, e ->
            if (e == null){
                if (objects.size > 0){
                    for (obj in objects){
                        var distance = geopoint.distanceInMilesTo(obj.get("Location") as ParseGeoPoint)
                        var distanceOneDp = (Math.round(distance * 10))/10
                        requests.add(distanceOneDp.toString() + "miles")
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,locationListener)
                    var lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    updateList(lastLocation)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)
        setTitle("Nearby Request")
        var requestList = findViewById<ListView>(R.id.request_list)

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,locationListener)
            var lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            updateList(lastLocation)
        }

        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,requests)
        requestList.setAdapter(adapter)
        //delay one day

    }
}
