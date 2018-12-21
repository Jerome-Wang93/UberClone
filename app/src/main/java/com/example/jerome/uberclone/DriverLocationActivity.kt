package com.example.jerome.uberclone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

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
        var intent = intent
        var markers = arrayListOf<Marker>()
        val driverLocation = LatLng(intent.getDoubleExtra("driverLatitude",0.0),
                intent.getDoubleExtra("driverLongitude",0.0))
        val riderLocation = LatLng(intent.getDoubleExtra("riderLatitude",0.0),
                intent.getDoubleExtra("riderLongitude",0.0))
        markers.add(mMap.addMarker(MarkerOptions().position(driverLocation).title("Marker in Driver")))
        markers.add(mMap.addMarker(MarkerOptions().position(riderLocation).title("Marker in Rider")))

        var builder = LatLngBounds.Builder()
        for ( ma in markers.indices){
            builder.include(markers[ma].position)
        }
        var bounds : LatLngBounds = builder.build()
        var padding = 0
        var cu = CameraUpdateFactory.newLatLngBounds(bounds,padding)
        mMap.animateCamera(cu)
    }
}
