package com.example.sawariapatkalinsewa.Mechanicui

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.repository.BusinessRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class UpdateMapActivity : AppCompatActivity() ,OnMapReadyCallback,View.OnClickListener{
    var mMap: GoogleMap? = null
    var geo: Geocoder? = null
    var txtMarkers: TextView? = null
    var btntype1: Button? = null
    lateinit var linearLayout:LinearLayout
    var btntype2: Button? = null
    var btntype3: Button? = null
    var lat:Double = 0.0
    var long:Double = 0.0
    var addressmap=""
    var latitudemap=""
    var longitudemap=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_map)
        txtMarkers = findViewById<View>(R.id.txtMarkerText) as TextView
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        lat = intent.getStringExtra("lat").toString().toDouble()
        long = intent.getStringExtra("long").toString().toDouble()
        btntype1 = findViewById<View>(R.id.btntype1) as Button
        btntype2 = findViewById<View>(R.id.btntype2) as Button
        btntype3 = findViewById<View>(R.id.btntype3) as Button
        linearLayout=findViewById(R.id.linearLayout)
        btntype1!!.setOnClickListener { if (mMap != null) mMap!!.mapType = GoogleMap.MAP_TYPE_SATELLITE }
        btntype2!!.setOnClickListener { if (mMap != null) mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL }
        btntype3!!.setOnClickListener(this)



    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat ,long),15F),3000,null)
        if (mMap != null) {
            geo = Geocoder(this, Locale.getDefault())
            mMap!!.setOnMapClickListener { latLng ->
                try {
                    if (geo == null) geo = Geocoder(this, Locale.getDefault())
                    val address = geo!!.getFromLocation(latLng.latitude, latLng.longitude, 1)
                    if (address.size > 0) {

                        mMap!!.addMarker(
                            MarkerOptions().position(latLng).title("Name:" + address[0].countryName
                                + ". Address:" + address[0].getAddressLine(0) ))

                        addressmap=address[0].getAddressLine(0).toString()
                        latitudemap=address[0].latitude.toString()
                        longitudemap=address[0].longitude.toString()

                        txtMarkers!!.text = ("Name:" + address[0].countryName
                                + ". Address:" + address[0].getAddressLine(0)+".Latitude:"+ address[0].latitude+".Longitude:"+address[0].longitude)
                    }
                } catch (ex: IOException) {
                    if (ex != null) Toast.makeText(this, "Error:" + ex.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
            mMap!!.setOnMarkerClickListener { marker ->
                txtMarkers!!.text = marker.title.toString() + " Lat:" + marker.position.latitude + " Long:" + marker.position.longitude
                false
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btntype3 ->{
                updateMap()
            }
        }
    }

    private fun updateMap() {
        val intent = intent.getParcelableExtra<Business>("business")

        val business=Business(address = addressmap,lat = latitudemap,long = longitudemap)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val businessRepository = BusinessRepository()
                val response = businessRepository.updateBusiness(intent?._id!!,business)
                if(response.success == true){
                    withContext(Dispatchers.Main) {
                        val snack = Snackbar.make(linearLayout, "Update SuccessFull", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@UpdateMapActivity,
                        ex.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}