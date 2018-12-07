package com.example.jerome.uberclone

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import java.util.jar.Manifest

class RiderActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var requestActive = false

    fun update(location : Location){
        var userLocation =  LatLng(location.latitude,location.longitude)
        mMap.clear()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15f))
        mMap.addMarker(MarkerOptions().position(userLocation).title("Your location"))
    }

    var locationListener = object : LocationListener{
        override fun onLocationChanged(location: Location) {
            update(location)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }
    }

    fun callUber(v : View){
        var button = findViewById(R.id.call) as Button
        if (requestActive){
            var query = ParseQuery<ParseObject>("Request")
            query.whereEqualTo("username",ParseUser.getCurrentUser().username)
            query.findInBackground { objects, e ->
                if (e == null){
                    if ( objects.size > 0){
                        for (obj in objects){
                            obj.delete()
                        }
                        button.setText("Call An Uber")
                        requestActive = false
                    }
                }
            }
        }else{
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            }else{
                var locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,locationListener)
                var lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (lastLocation != null){
                    var request = ParseObject("Request")
                    request.put("username",ParseUser.getCurrentUser().username)
                    var location = ParseGeoPoint(lastLocation.latitude,lastLocation.longitude)
                    request.put("Location",location)
                    request.saveInBackground {
                        if (it == null){
                            button.setText("Cancel Uber")
                            requestActive = true
                        }else{
                            Log.i("Info","Request save error: " + it.message)
                        }
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
                    var locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,locationListener)
                    var lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    update(lastLocation)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rider)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (Build.VERSION.SDK_INT < 23){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,locationListener)
        }else{
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,locationListener)
                var lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (lastLocation != null){
                    update(lastLocation)
                }
            }
        }

    }
}

