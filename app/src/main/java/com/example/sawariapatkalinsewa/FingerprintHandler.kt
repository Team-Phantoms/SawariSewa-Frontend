package com.example.sawariapatkalinsewa

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.sawariapatkalinsewa.Customerui.DashBoardActivity
import com.example.sawariapatkalinsewa.Mechanicui.MechDashboardActivity
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import kotlinx.coroutines.*


@RequiresApi(Build.VERSION_CODES.M)
class FingerprintHandler(private val appContext: Context) : FingerprintManager.AuthenticationCallback() {

    private var cancellationSignal: CancellationSignal? = null
    private var clusername: String = ""
    private var clpassword: String = ""
    private var type: String = ""

    fun startAuth(
        manager: FingerprintManager,
        cryptoObject: FingerprintManager.CryptoObject
    ) {
        getSharedPref()
        cancellationSignal = CancellationSignal()

        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.USE_FINGERPRINT
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    private fun getSharedPref() {
        val sharedPref = appContext.getSharedPreferences("LoginPref", AppCompatActivity.MODE_PRIVATE)
        clusername = sharedPref.getString("username", "").toString()
        clpassword = sharedPref.getString("password", "").toString()
        type = sharedPref.getString("type", "").toString()
    }
    override fun onAuthenticationError(
        errMsgId: Int,
        errString: CharSequence
    ) {
        Toast.makeText(
            appContext,
            "Authentication error\n" + errString,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onAuthenticationHelp(
        helpMsgId: Int,
        helpString: CharSequence
    ) {
        Toast.makeText(
            appContext,
            "Authentication help\n" + helpString,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onAuthenticationFailed() {
        Toast.makeText(
            appContext,
            "Authentication failed.",
            Toast.LENGTH_LONG
        ).show()
    }
    private fun login() {
        try {
            GlobalScope.launch {
                delay(4000)
                val repository = CustomerRepository()
                val response = repository.loginUser(clusername, clpassword)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    ServiceBuilder.username ="${response.data}"
                  this@FingerprintHandler.appContext.startActivity(Intent(this@FingerprintHandler.appContext,DashBoardActivity::class.java))

                }
            }

        } catch (ex: Exception) {

            Toast.makeText(
                this.appContext,
                "Login error", Toast.LENGTH_SHORT
            ).show()

        }
    }
    private fun mechlogin() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = MechanicRepository()
                val response = repository.loginMech(clusername, clpassword)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    ServiceBuilder.username ="${response.data}"

                    this@FingerprintHandler.appContext.startActivity(
                        Intent(this@FingerprintHandler.appContext,
                            MechDashboardActivity::class.java
                        )

                    )


                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@FingerprintHandler.appContext,
                            "Invalid Login", Toast.LENGTH_SHORT
                        ).show()
                    }
                }



            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@FingerprintHandler.appContext,
                        "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    }
    override fun onAuthenticationSucceeded(
        result: FingerprintManager.AuthenticationResult
    ) {

//       this.appContext.startActivity(
//            Intent(
//              this.appContext,DashBoardActivity::class.java
//            )
//        )
        if (type == "Customer") {
            if (clusername == "") {
                Toast.makeText(
                    this.appContext,
                    "Looks like you have loggedOut please login again",
                    Toast.LENGTH_LONG
                ).show()
                this.appContext.startActivity(
                    Intent(
                        this.appContext, LoginActivity::class.java
                    )
                )
            } else {

                login()
            }
            Toast.makeText(
                appContext,
                "Authentication succeeded.",
                Toast.LENGTH_LONG
            ).show()
        }
        else if (type=="Mechanic"){
            if (clusername == "") {
                Toast.makeText(
                    this.appContext,
                    "Looks like you have loggedOut please login again",
                    Toast.LENGTH_LONG
                ).show()
                this.appContext.startActivity(
                    Intent(
                        this.appContext, LoginActivity::class.java
                    )
                )
            } else {
                mechlogin()
            }
            Toast.makeText(
                appContext,
                "Authentication succeeded.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}