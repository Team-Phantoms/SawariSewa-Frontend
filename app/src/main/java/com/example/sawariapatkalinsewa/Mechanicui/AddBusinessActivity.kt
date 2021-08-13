package com.example.sawariapatkalinsewa.Mechanicui

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.sawariapatkalinsewa.LoginActivity
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.repository.BusinessRepository
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.google.android.gms.location.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class AddBusinessActivity : AppCompatActivity(), View.OnClickListener,SensorEventListener {
    private lateinit var etFullName: TextInputEditText
    private lateinit var etusername: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var etLocationLat: TextInputEditText
    private lateinit var etLocationLong: TextInputEditText
    private lateinit var rdoMale: RadioButton
    private lateinit var rdoFemale: RadioButton
    private lateinit var rdoOthers: RadioButton
    private lateinit var btnSave: Button
    private lateinit var btnaddress: Button
    private lateinit var linearLayout: LinearLayout
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1010
    lateinit var sensorManager: SensorManager
    var sensor: Sensor?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_business)

        etFullName = findViewById(R.id.etFullName)
        etusername = findViewById(R.id.etusername)
        etAge = findViewById(R.id.etAge)
        etAddress = findViewById(R.id.etAddress)
        etLocationLat = findViewById(R.id.etLocationlat)
        etLocationLong = findViewById(R.id.etLocationlong)
        rdoMale = findViewById(R.id.rdoMale)
        rdoFemale = findViewById(R.id.rdoFemale)
        rdoOthers = findViewById(R.id.rdoOthers)
        btnSave = findViewById(R.id.btnSave)
        btnaddress=findViewById(R.id.btnaddress)
        linearLayout=findViewById(R.id.linearLayout)
etusername.setText("${ServiceBuilder.username}")


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        btnaddress.setOnClickListener(this)

        btnSave.setOnClickListener(this)
        sensorManager=getSystemService(SENSOR_SERVICE) as SensorManager
        if (!checkSensor())
            return
        else{
            sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnSave -> {
                saveDetail()
            }
            R.id.btnaddress -> {
                Log.d("Debug:", CheckPermission().toString())
                Log.d("Debug:", isLocationEnabled().toString())
                RequestPermission()

                getLastLocation()
            }
        }
    }
    private fun checkSensor(): Boolean {
        var flag=true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)==null){
            flag=false
        }
        return flag
    }
    private fun saveDetail() {
        val username=etusername.text.toString()
        val fullname = etFullName.text.toString()
        val phone = etAge.text.toString()
        val address = etAddress.text.toString()
        val lat=etLocationLat.text.toString()
        val long=etLocationLong.text.toString()
        var gender = ""
        when {
            rdoFemale.isChecked -> {
                gender = "Four Wheelers"
            }
            rdoMale.isChecked -> {
                gender = "Two Wheelers"
            }
            rdoOthers.isChecked -> {
                gender = "Both"
            }
        }
        val business = Business(
            fullname = fullname,
            phone = phone,
            gender = gender,
            address = address,
            lat = lat,
            long = long,
                mechusername =username


        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val businessRepository = BusinessRepository()
                val response =  businessRepository.registerBusiness(business)
                    if(response.success == true){
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    this@AddBusinessActivity,
                                    "Business Details added", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AddBusinessActivity,
                        ex.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

}
    fun getLastLocation(){
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        val geocoder: Geocoder
                        val addresses: List<Address>
                        geocoder = Geocoder(this, Locale.getDefault())

                        addresses = geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


                        val address =
                            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                        Log.d("Debug:", "Your Location:" + location.longitude)
                        etAddress.setText(
                            address
                        )
                        etLocationLong.setText(" ${location.longitude}  ")
                        etLocationLat.setText(" ${location.latitude}  ")
                    }
                }
            }else{
                Toast.makeText(this, "Please Turn on Your device Location", Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }


    fun NewLocationData(){
        locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
            etAddress.setText(
                getCityName(
                    lastLocation.latitude,
                    lastLocation.longitude
                )
            )
            etLocationLong.setText(" ${lastLocation.longitude}  ")
            etLocationLat.setText(" ${lastLocation.latitude}  ")
        }
    }

    private fun CheckPermission():Boolean{
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if(
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }

    fun RequestPermission(){
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:", "You have the Permission")
            }
        }
    }

    private fun getCityName(lat: Double, long: Double): String {
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geoCoder.getFromLocation(lat, long, 1)

        cityName = addresses.get(0).locality
        countryName = addresses.get(0).countryName
        Log.d("Debug:", "Your City: " + cityName + " ; your Country " + countryName)
        return cityName
    }


    private fun logOut(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout ??")
        builder.setIcon(android.R.drawable.ic_delete)

        builder.setPositiveButton("Yes") { _, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                val custRepo = CustomerRepository()
                val response=custRepo.logout()
                if(response.success==true){
                    withContext(Dispatchers.Main){
                        getSharedPreferences("LoginPref", MODE_PRIVATE)?.edit()?.clear()
                            ?.apply();
                        val intent = Intent(this@AddBusinessActivity, LoginActivity::class.java);
                        startActivity(intent);
                    }
                }
            }
        }
        builder.setNegativeButton("No") { _, _ ->
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values=event!!.values[1]
        if (values<-2){
            logOut()
        }
        else if (values>2){
            val intent = Intent(this@AddBusinessActivity, MechDashboardActivity::class.java);
            startActivity(intent);
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }


}
