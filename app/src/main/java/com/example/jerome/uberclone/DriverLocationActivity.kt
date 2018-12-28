package com.example.jerome.uberclone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class DriverLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_location)
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        /*var intent = intent
        Log.i("receive",intent.getDoubleExtra("driverLatitude",0.0).toString())
        val driverLocation = LatLng(intent.getDoubleExtra("driverLatitude",0.0),
                intent.getDoubleExtra("driverLongitude",0.0))

        mMap.addMarker(MarkerOptions().position(driverLocation).title("Marker in Driver"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(driverLocation))*/
        var lat = LatLng(-34.0,151.0)
        mMap.addMarker(MarkerOptions().position(lat).title("sss"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lat))
    }
}
