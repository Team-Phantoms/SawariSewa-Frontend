package com.example.sawariapatkalinsewa

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.example.sawariapatkalinsewa.Customerui.DashBoardActivity
import com.example.sawariapatkalinsewa.Mechanicui.MechDashboardActivity
import com.example.sawariapatkalinsewa.adapter.ScreenSlidePageAdapter
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


@Suppress("DEPRECATION")
class IntroductaryActivity : AppCompatActivity() {
    private lateinit var Ivbg: ImageView
    private lateinit var Ivlogo: ImageView
    private lateinit var tvName: TextView
    private lateinit var lottie: LottieAnimationView
    private var clusername: String = ""
    private var clpassword: String = ""
    private var type: String = ""

    private lateinit var viewpager: ViewPager
    lateinit var anim: Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introductary)

        Ivbg = findViewById(R.id.Ivbg)
        Ivlogo = findViewById(R.id.Ivlogo)
        tvName = findViewById(R.id.tvName)
        lottie = findViewById(R.id.lottie)
        viewpager = findViewById(R.id.pager)
        val pagerAdapter = ScreenSlidePageAdapter(supportFragmentManager)
        viewpager.adapter = pagerAdapter

        anim = AnimationUtils.loadAnimation(this, R.anim.o_b_animation)
        viewpager.startAnimation(anim)


        Ivbg.animate().translationY(-3500f).setDuration(2000).setStartDelay(4000)
        Ivlogo.animate().translationY(2500f).setDuration(2000).setStartDelay(4000)
        tvName.animate().translationY(2500f).setDuration(2000).setStartDelay(4000)
        lottie.animate().translationY(2500f).setDuration(2000).setStartDelay(4000)

        getSharedPref()
        if (type=="Customer") {
            if (clusername == "") {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(7000)
                    startActivity(Intent(this@IntroductaryActivity, LoginActivity::class.java))
                    finish()
                }
            } else {
                login()
            }
        }
        else if (type=="Mechanic"){
            if (clusername == "") {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(7000)
                    startActivity(Intent(this@IntroductaryActivity, LoginActivity::class.java))
                    finish()
                }
            } else {
                mechlogin()
            }
        }

    }


    private fun getSharedPref() {
        val sharedPref = getSharedPreferences("LoginPref", AppCompatActivity.MODE_PRIVATE)
        clusername = sharedPref.getString("username", "").toString()
        clpassword = sharedPref.getString("password", "").toString()
        type = sharedPref.getString("type", "").toString()
    }

    private fun login() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                delay(4000)
                val repository = CustomerRepository()
                val response = repository.loginUser(clusername, clpassword)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    ServiceBuilder.username ="${response.data}"
                    startActivity(
                            Intent(
                                    this@IntroductaryActivity,
                                    LoginActivity::class.java
                            )
                    )

                }
            }

        } catch (ex: Exception) {

            Toast.makeText(
                    this,
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

                    startActivity(
                        Intent(this@IntroductaryActivity,
                            MechDashboardActivity::class.java
                        )

                    )


                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@IntroductaryActivity,
                            "Invalid Login", Toast.LENGTH_SHORT
                        ).show()
                    }
                }



            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@IntroductaryActivity,
                        "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    }


}