package com.example.sawariapatkalinsewa.ui.gallery

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.client
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import de.hdodenhof.circleimageview.CircleImageView
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
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment: Fragment() {
    private lateinit var tv_name:TextView
    private lateinit var tv_address:TextView
    private lateinit var tv_phone:TextView
    private lateinit var tv_email:TextView
    private lateinit var tv_email2:TextView
    private lateinit var tv_fname:TextView
    private lateinit var tv_lname:TextView
    private lateinit var imgview:ImageView
    private lateinit var update:TextView
    private var type: String = ""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        tv_name=root.findViewById(R.id.tv_name)
        tv_address=root.findViewById(R.id.tv_address)
        tv_phone=root.findViewById(R.id.tv_phone)
        tv_email=root.findViewById(R.id.tv_email)
        tv_email2=root.findViewById(R.id.tv_email2)
        tv_fname=root.findViewById(R.id.tv_fname)
        tv_lname=root.findViewById(R.id.tv_lname)
        imgview=root.findViewById(R.id.imgview)
        update=root.findViewById(R.id.update)

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
                            tv_email2.text=listprofile[0].clemail
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
                            tv_email2.text=listprofile[0].mechaddress
                            tv_phone.text=listprofile[0].mechPhone
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
        startActivityForResult(cameraIntent,REQUEST_CAMERA_CODE)
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
                    RequestBody.create(MediaType.parse("image/"+file.extension.toLowerCase().replace("jpg","jpeg")), file)
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

}
