package com.example.sawariapatkalinsewa.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sawariapatkalinsewa.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GalleryFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.activity_maps, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return root
    }

    override fun onMapReady(p0: GoogleMap?) {
        if (p0 != null) {
            mMap = p0
        }
        mMap.addMarker(
            MarkerOptions().position(LatLng(27.6724375, 85.4043844))
                .title("Mero Stop")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(27.6724375, 85.4043844),16F),3000,null
        )
        mMap.uiSettings.isZoomControlsEnabled=true


    }
}