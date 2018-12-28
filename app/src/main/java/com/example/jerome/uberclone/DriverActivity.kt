package com.example.jerome.uberclone

import android.content.Context
import android.content.Intent
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
    lateinit var locationManager : LocationManager
    var requestLatitudes = arrayListOf<Double>()
    var requestLongitudes = arrayListOf<Double>()


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

    fun updateList(location: Location) {
        var query = ParseQuery<ParseObject>("Request")
        var geoPoint = ParseGeoPoint(location.latitude,location.longitude)
        query.whereNear("Location",geoPoint)
        query.findInBackground { objects, e ->
            if (e == null){
                requests.clear()
                requestLongitudes.clear()
                requestLatitudes.clear()
                if (objects.size > 0){
                    for (  i in objects.indices){
                        var riderLocation = (objects[i].get("Location")) as ParseGeoPoint
                        var distanceInMiles = geoPoint.distanceInMilesTo(riderLocation)
                        var disOneDp = Math.round(distanceInMiles * 10) / 10
                        requests.add(distanceInMiles.toString() + " miles")
                        adapter.notifyDataSetChanged()
                        requestLatitudes.add(riderLocation.latitude)
                        requestLongitudes.add(riderLocation.longitude)
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
                    locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,requests)
        var requestList = findViewById<ListView>(R.id.request_list)
        requestList.setAdapter(adapter)


        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
        }else{
            locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,locationListener)
            var lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            updateList(lastLocation)
        }

        requestList.setOnItemClickListener { parent, view, position, id ->
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            }else{
                locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                var lLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if ( requestLatitudes.size > position && requestLongitudes.size > position){
                    var intent = Intent(applicationContext,DriverLocationActivity :: class.java)
                    intent.putExtra("riderLatitude",requestLatitudes[position])
                    intent.putExtra("riderLongitude",requestLongitudes[position])
                    intent.putExtra("driverLatitude",lLocation.latitude)
                    intent.putExtra("driverLongitude",lLocation.longitude)
                    Log.i("DriverActivity-test",lLocation.latitude.toString() +
                            "," + lLocation.longitude.toString())
                    Log.i("DriverActivity-test",requestLatitudes[position].toString() +
                            "," + requestLongitudes[position].toString())

                    startActivity(intent)

                }
            }
        }
    }
}
