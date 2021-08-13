package com.example.sawariapatkalinsewa.Mechanicui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sawariapatkalinsewa.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MechanicMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
     var lat:Double = 0.0
     var long:Double = 0.0
    var user=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mechanic_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         lat = intent.getStringExtra("lat").toString().toDouble()
         long = intent.getStringExtra("long").toString().toDouble()
         user = intent.getStringExtra("user").toString()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        mMap.addMarker(MarkerOptions().position(LatLng(lat,long)).title("${user} Location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat,long),15F),3000,null)

        mMap.uiSettings.isZoomControlsEnabled=true
    }
}
