package com.example.sawariapatkalinsewa.ui.gallery

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import com.google.android.gms.location.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment: Fragment(), View.OnClickListener {
    private lateinit var tv_name:TextView
    private lateinit var tv_address: TextInputEditText
    private lateinit var tv_phone:EditText
    private lateinit var tv_email:TextView
    private lateinit var tv_caddres:TextView
    private lateinit var tv_email2:EditText
    private lateinit var tv_fname:EditText
    private lateinit var tv_lname:EditText
    private lateinit var imgview:ImageView
    private lateinit var update:TextView
    private lateinit var btnForm:Button
    private var type: String = ""
    val PERMISSION_ID = 1010
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient //for address
    lateinit var locationRequest: LocationRequest //for address
    lateinit var locationManager: LocationManager



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        tv_name=root.findViewById(R.id.tv_name)
        tv_address=root.findViewById(R.id.tv_address)
        tv_caddres=root.findViewById(R.id.tv_caddres)
        tv_phone=root.findViewById(R.id.tv_phone)
        tv_email=root.findViewById(R.id.tv_email)
        tv_email2=root.findViewById(R.id.tv_email2)
        tv_fname=root.findViewById(R.id.tv_fname)
        tv_lname=root.findViewById(R.id.tv_lname)
        imgview=root.findViewById(R.id.imgview)
        update=root.findViewById(R.id.update)
        btnForm=root.findViewById(R.id.btnForm)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext()) //for location


        getSharedPref()
        if (type=="Customer") {
            getProfile()
        }
        else if (type=="Mechanic"){
            getMProfile()
        }
        imgview.setOnClickListener{
            loadPopUpMenu()
        }
        update.setOnClickListener {
            uploadImage()
        }
        tv_caddres.setOnClickListener(this)
        return root
    }
    private fun getSharedPref() {
        val sharedPref = context?.getSharedPreferences("LoginPref", AppCompatActivity.MODE_PRIVATE)
        if (sharedPref != null) {
            type = sharedPref.getString("type", "").toString()
        }
    }
    private fun getProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val customerRepository = CustomerRepository()
                val response = customerRepository.getcustomer()
                if(response.success == true){
                    val listprofile = response.cprofile
                    if (listprofile != null) {
                        withContext(Dispatchers.Main){
                            Log.d("Debug:", "Your data:" + listprofile[0])
                            tv_name.text=listprofile[0].clusername
                            tv_email.text=listprofile[0].clemail
                            tv_fname.setText("${listprofile[0].clfname}")
                            tv_lname.setText("${listprofile[0].cllname}")

                            val imagePath = ServiceBuilder.loadImagePath() + listprofile[0].photo
                                Glide.with(this@ProfileFragment)
                                        .load(imagePath)
                                        .fitCenter()
                                        .into(imgview)
                        }
                    }
                }

            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,
                            "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getMProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mechanicRepository = MechanicRepository()
                val response = mechanicRepository.getMech()
                if(response.success == true){
                    val listprofile = response.profile
                    if (listprofile != null) {
                        withContext(Dispatchers.Main){
                            Log.d("Debug:", "Your data:" + listprofile[0])
                            tv_name.text=listprofile[0].mechusername
                            tv_email.text=listprofile[0].mechemail
                            tv_fname.setText("${listprofile[0].mechfname}")
                            tv_lname.setText("${listprofile[0].mechlname}")

//                            val imagePath = ServiceBuilder.loadImagePath() + listprofile[0].photo
//                            Glide.with(this@ProfileFragment)
//                                .load(imagePath)
//                                .fitCenter()
//                                .into(imgview)
                        }
                    }
                }

            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,
                            "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun loadPopUpMenu() {
        // Load pop up menu

        val popupMenu = PopupMenu(context, imgview)
        popupMenu.menuInflater.inflate(R.menu.gallery_camers, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()

    }
    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl=""
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = requireActivity().contentResolver
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                imgview.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                imgview.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }

    }

    private fun bitmapToFile(bitmap: Bitmap, fileName: String): File? {

        var file: File? = null
        return try {
            file = File(
                    requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileName
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    private fun uploadImage() {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                    RequestBody.create(MediaType.parse("image/" + file.extension.toLowerCase().replace("jpg", "jpeg")), file)
            val body =
                    MultipartBody.Part.createFormData("photo", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val customerRepository = CustomerRepository()
                    val response = customerRepository.uploadImage(body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Mero Error ", ex.localizedMessage)
                        Toast.makeText(
                                context,
                                ex.localizedMessage,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.tv_caddres -> { //for address
                Log.d("Debug:", CheckPermission().toString())
                Log.d("Debug:", isLocationEnabled().toString())
                RequestPermission()
                getLastLocation()
            }
        }
    }

    private fun getLastLocation() {
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (context?.let {
                            ActivityCompat.checkSelfPermission(
                                    it,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        } != PackageManager.PERMISSION_GRANTED && context?.let {
                            ActivityCompat.checkSelfPermission(
                                    it,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        } != PackageManager.PERMISSION_GRANTED
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
                        geocoder = Geocoder(context, Locale.getDefault())

                        addresses = geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        val address =
                                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                        Log.d("Debug:", "Your Location:" + location.longitude)
                        tv_address.setText(
                                address
                        )
//                        etrLocationLong.setText(" ${location.longitude}  ")
//                        etrLocationLat.setText(" ${location.latitude}  ")
                    }
                }
            }else{
               Toast.makeText(context, "Please Turn on Your device Location", Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }

    private fun NewLocationData() {
        locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext()) //for location
        if (context?.let {
                    ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED && context?.let {
                    ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED
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
            tv_address.setText(
                    getCityName(
                            lastLocation.latitude,
                            lastLocation.longitude
                    )
            )
//            etrLocationLong.setText(" ${lastLocation.longitude}  ")
//            etrLocationLat.setText(" ${lastLocation.latitude}  ")
        }
    }

    private fun getCityName(lat: Double, long: Double): String {
        var cityName: String
        var countryName: String
        var geoCoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geoCoder.getFromLocation(lat, long, 1)

        cityName = addresses.get(0).locality
        countryName = addresses.get(0).countryName
        Log.d("Debug:", "Your City: " + cityName + " ; your Country " + countryName)
        return cityName

    }

    private fun RequestPermission() {
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
        )
    }

    private fun CheckPermission(): Boolean {
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if(
                context?.let {
                    ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED ||
                context?.let {
                    ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }

    private fun isLocationEnabled(): Boolean {
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        locationManager= (requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager)!!
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )

    }


}




