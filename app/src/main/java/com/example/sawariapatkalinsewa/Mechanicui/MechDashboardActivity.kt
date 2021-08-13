package com.example.sawariapatkalinsewa.Mechanicui



import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.sawariapatkalinsewa.Customerui.ViewMechanicActivity
import com.example.sawariapatkalinsewa.LoginActivity
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.channel.NotificationChannels
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MechDashboardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var mechname:String="";
    private var mechphone:String="";
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mech_dashboard)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val logout: ImageView =findViewById(R.id.ivmlogout)
        setSupportActionBar(toolbar)
        checkRunTimePermission()
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            viewRequest()
            showHighPriorityNotification()
        }
        logout.setOnClickListener{
            logOut()
        }
        getMProfile()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_mechfragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_mechhome, R.id.nav_profile, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun viewRequest() {
        val intent=Intent(this, ViewRequestActivity::class.java)
        intent.putExtra("mechname",mechname)
        intent.putExtra("mechphone",mechphone)
        startActivity(intent)
        Log.d("Debug:", "Your data:" + mechname+mechphone)
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
                            mechname= listprofile[0].mechusername.toString()
                            mechphone= listprofile[0].mechPhone.toString()


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
                    Toast.makeText(this@MechDashboardActivity,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun logOut(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout ??")
        builder.setIcon(android.R.drawable.ic_delete)

        builder.setPositiveButton("Yes") { _, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                val mechRepository = MechanicRepository()
                val response=mechRepository.logout()
                if(response.success==true){
                    withContext(Dispatchers.Main){
                        getSharedPreferences("LoginPref", MODE_PRIVATE)?.edit()?.clear()
                                ?.apply();
                        val intent = Intent(this@MechDashboardActivity, LoginActivity::class.java);
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
    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 1)
    }
    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }
    private fun showHighPriorityNotification() {
        val notificationManager= NotificationManagerCompat.from(this)

        val notificationChannels= NotificationChannels(this)
        notificationChannels.createNotificationChannels()

        val notification= NotificationCompat.Builder(this,notificationChannels.CHANNEL_1)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Mechanic")
            .setContentText("You searched for mechanic")
            .setColor(Color.BLACK)
            .build()

        notificationManager.notify(1,notification)
    }
    private fun viewMechanic(){
        startActivity(
            Intent(
                this, ViewMechanicActivity::class.java
            )
        )
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_mechfragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}