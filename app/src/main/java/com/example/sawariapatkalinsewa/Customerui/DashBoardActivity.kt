package com.example.sawariapatkalinsewa.Customerui

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
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
import com.example.sawariapatkalinsewa.LoginActivity
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.channel.NotificationChannels
import com.example.sawariapatkalinsewa.repository.BusinessRepository
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class DashBoardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        val logout:ImageView=findViewById(R.id.ivlogout)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        checkRunTimePermission()
        fab.setOnClickListener { view ->
            viewMechanic()
            showHighPriorityNotification()
        }

        logout.setOnClickListener{
            logOut()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_profile), drawerLayout)
      setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)
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
                    withContext(Main){
                        getSharedPreferences("LoginPref", MODE_PRIVATE)?.edit()?.clear()
                            ?.apply();
                        val intent = Intent(this@DashBoardActivity, LoginActivity::class.java);
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
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }




}