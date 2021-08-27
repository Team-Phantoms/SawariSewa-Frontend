package com.example.sawariapatkalinsewa.ui.home

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sawariapatkalinsewa.Customerui.AddRequestActivity
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.api.ServiceBuilder

class HomeFragment : Fragment(),View.OnClickListener,SensorEventListener {
private lateinit var cvflatetire:CardView
private lateinit var cvbreakdown:CardView
private lateinit var cvjumpstart:CardView
private lateinit var cvlockedout:CardView
private lateinit var cvaccident:CardView
private lateinit var cvfuel:CardView
private lateinit var cvSome:CardView
lateinit var username:TextView
 var problem=""
    lateinit var sensorManager: SensorManager
    var sensor: Sensor?=null
    lateinit var slview: LinearLayout
    lateinit var slview2: LinearLayout
    lateinit var slview3: LinearLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        username=root.findViewById(R.id.username)
        cvflatetire=root.findViewById(R.id.cvflatetire)
        cvbreakdown=root.findViewById(R.id.cvbreakdown)
        cvjumpstart=root.findViewById(R.id.cvjumpstart)
        cvlockedout=root.findViewById(R.id.cvlockedout)
        cvaccident=root.findViewById(R.id.cvaccident)
        cvfuel=root.findViewById(R.id.cvfuel)
        slview=root.findViewById(R.id.linearLayout)
        slview2=root.findViewById(R.id.linearLayout2)
        slview3=root.findViewById(R.id.line1)
        cvSome=root.findViewById(R.id.cvSome)

        cvflatetire.setOnClickListener(this)
        cvbreakdown.setOnClickListener(this)
        cvjumpstart.setOnClickListener(this)
        cvlockedout.setOnClickListener(this)
        cvaccident.setOnClickListener(this)
        cvfuel.setOnClickListener(this)
        cvSome.setOnClickListener(this)

        username.text="Welcome ${ServiceBuilder.username}"

        sensorManager= context?.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        if (!checkSensor()) {
            return root
        }
        else{
            sensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
        }

        return root
    }

    private fun checkSensor(): Boolean {
        var flag=true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)==null){
            flag=false
        }
        return flag
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.cvflatetire -> {
                problem="FlatTire"
                senddata()
            }
            R.id.cvbreakdown -> {
                problem="BreakDown"
                senddata()
            }
            R.id.cvjumpstart -> {
                problem="JumpStart"
                senddata()
            }
            R.id.cvlockedout -> {
                problem="LockedOut"
                senddata()
            }
            R.id.cvaccident -> {
                problem="Accident"
                senddata()
            }
            R.id.cvfuel -> {
                problem="Fuel Out"
                senddata()
            }
            R.id.cvSome ->{
                problem=""
                senddata()
            }
        }
    }

    private fun senddata() {
        startActivity(
            Intent(
                context,AddRequestActivity::class.java
            ).putExtra("problem",problem)
        )
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values=event!!.values[0]
        if (values>20){
            slview.setBackgroundResource(R.color.black)
            slview2.setBackgroundResource(R.color.black)
            slview3.setBackgroundResource(R.color.black)
        }
        else if(values<20){
            slview.setBackgroundResource(R.color.white)
            slview2.setBackgroundResource(R.color.white)
            slview3.setBackgroundResource(R.color.white)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}