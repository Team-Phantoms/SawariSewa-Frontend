package com.example.sawariapatkalinsewa.Customerui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.fragment.AddvehicleFragment
import com.example.sawariapatkalinsewa.fragment.ViewVehicleFragment

class VehicleActivity : AppCompatActivity() {
    private lateinit var addvehicle:TextView
    private lateinit var viewvehicle:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)

        addvehicle=findViewById(R.id.tvvehicleadd)
        viewvehicle=findViewById(R.id.tvvehicleview)

        addvehicle.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.linearContainer,AddvehicleFragment())
                addToBackStack(null)
                commit()
            }
        }

        viewvehicle.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.linearContainer,ViewVehicleFragment())
                addToBackStack(null)
                commit()
            }
        }

    }
}