package com.example.sawariapatkalinsewa.Customerui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.core.text.isDigitsOnly
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.Request
import com.example.sawariapatkalinsewa.repository.BusinessRepository
import com.example.sawariapatkalinsewa.repository.RequestMechRepository
import com.example.sawariapatkalinsewa.repository.VehicleRepository
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern

const val TOPIC = "/topics/myTopic2"
class AddRequestActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var tvproblemtype:TextInputEditText
    private lateinit var rvechbrand: TextInputEditText
    private lateinit var rvechmodel: TextInputEditText
    private lateinit var rplatenum: TextInputEditText
    private lateinit var etrAddress: TextInputEditText
    private lateinit var etrLocationLat: TextInputEditText
    private lateinit var token: TextInputEditText
    private lateinit var etrLocationLong: TextInputEditText
    private lateinit var contact: TextInputEditText
    private lateinit var btnraddress:Button
    private lateinit var btnrequest:Button
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient //for address
    lateinit var locationRequest: LocationRequest //for address
    val PERMISSION_ID = 1010
    var problem=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_request)
        problem = intent.getStringExtra("problem").toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }


        tvproblemtype=findViewById(R.id.tvproblemtype)
        rvechbrand = findViewById(R.id.rvechbrand)
        rvechmodel = findViewById(R.id.rvechmodel)
        rplatenum = findViewById(R.id.rplatenum)
        etrAddress = findViewById(R.id.etraddress)
        etrLocationLat = findViewById(R.id.etrLocationlat)
        etrLocationLong = findViewById(R.id.etrLocationlong)
        contact = findViewById(R.id.contact)
        token=findViewById(R.id.token)
        btnraddress=findViewById(R.id.btnraddress)
        btnrequest=findViewById(R.id.btnrequest)
        contact.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mobileValidate(contact.text.toString()))
                {
                    btnrequest.isEnabled=true
                }
                else{
                    btnrequest.isEnabled=false
                    contact.setError("Invalid Phone")
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this) //for location

        getRVehicle()
        btnraddress.setOnClickListener(this) //address for location
        btnrequest.setOnClickListener(this)

        tvproblemtype.setText(problem)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("debug", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val tokenres = task.result
            token.setText(tokenres)
            Log.d("debug","token $tokenres")
        })
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)



    }
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnraddress -> { //for address
                Log.d("Debug:", CheckPermission().toString())
                Log.d("Debug:", isLocationEnabled().toString())
                RequestPermission()
                getLastLocation()
            }
            R.id.btnrequest -> {
                addProblem()
            }
        }
    }

    private fun addProblem() {

            val problemtype=tvproblemtype.text.toString()
            val vechbrand  = rvechbrand.text.toString()
            val vechmodel = rvechmodel.text.toString()
            val vechplatenum = rplatenum.text.toString()
            val address = etrAddress.text.toString()
            val lat=etrLocationLat.text.toString()
            val long=etrLocationLong.text.toString()
            val token=token.text.toString()
            val contact=contact.text.toString()
if (problemtype.isEmpty()){
    tvproblemtype.error="Problem Type Required"
}
else if (!tvproblemtype.text.toString().matches("^[a-zA-Z]*$".toRegex())){
    tvproblemtype.error="Problem Type Doesnot accept digits"
}
       else if (vechbrand.isEmpty()){
            rvechbrand.error="Problem Type Required"
        }
else if (vechmodel.isEmpty()){
    rvechmodel.error="Problem Type Required"
}
else if (vechplatenum.isEmpty()){
    rplatenum.error="Problem Type Required"
}
else if (address.isEmpty()){
    etrAddress.error="Problem Type Required"
}
else if (lat.isEmpty()){
    etrLocationLat.error="Problem Type Required"
}
else if (long.isEmpty()){
    etrLocationLong.error="Problem Type Required"
}

            val request = Request(
                    problemtype = problemtype,
                    vechbrand = vechbrand,
                    vechmodel = vechmodel,
                    vechplatenum = vechplatenum,
                    address = address,
                    lat = lat,
                    long = long,
                    token=token,
                    clusername = ServiceBuilder.username,
                    contact=contact


            )
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val requestMechRepository=RequestMechRepository()
                    val response =  requestMechRepository.requestMech(request)
                    if(response.success == true){
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    this@AddRequestActivity,
                                    "Request Sent", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@AddRequestActivity,
                                ex.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
    private fun mobileValidate(text: String): Boolean {
        val p= Pattern.compile("[0-9][0-9]{9}")
        var m =p.matcher(text)
        return m.matches()
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
                        etrAddress.setText(
                            address
                        )
                        etrLocationLong.setText(" ${location.longitude}  ")
                        etrLocationLat.setText(" ${location.latitude}  ")
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
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
            etrAddress.setText(
                getCityName(
                    lastLocation.latitude,
                    lastLocation.longitude
                )
            )
            etrLocationLong.setText(" ${lastLocation.longitude}  ")
            etrLocationLat.setText(" ${lastLocation.latitude}  ")
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:", "You have the Permission")
            }
        }
    }

    private fun getCityName(lat: Double, long: Double): String {
        var cityName: String
        var countryName: String
        var geoCoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geoCoder.getFromLocation(lat, long, 1)

        cityName = addresses.get(0).locality
        countryName = addresses.get(0).countryName
        Log.d("Debug:", "Your City: " + cityName + " ; your Country " + countryName)
        return cityName
    }

    private fun getRVehicle() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val vehicleRepository = VehicleRepository()
                val response = vehicleRepository.getVehicle()
                if (response.success==true){
                    val listvehicle = response.vdata
                    if (listvehicle != null) {
                        withContext(Dispatchers.Main){
                            Log.d("Debug:", "Your data:" + listvehicle[0])
                            rvechbrand.setText("${listvehicle[0].vechbrand}")
                            rvechmodel.setText("${listvehicle[0].vechmodel}")
                            rplatenum.setText("${listvehicle[0].vechplatenum}")


                        }


                    }
                }


            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@AddRequestActivity,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}