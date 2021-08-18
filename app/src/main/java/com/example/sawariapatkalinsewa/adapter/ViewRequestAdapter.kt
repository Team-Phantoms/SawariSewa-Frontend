package com.example.sawariapatkalinsewa.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.channel.NotificationData
import com.example.sawariapatkalinsewa.channel.PushNotification
import com.example.sawariapatkalinsewa.channel.RetrofitInstance
import com.example.sawariapatkalinsewa.entity.Request
import com.example.sawariapatkalinsewa.entity.workHistory
import com.example.sawariapatkalinsewa.repository.RequestMechRepository
import com.example.sawariapatkalinsewa.repository.workHistoryRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ViewRequestAdapter (
    val lstbusiness: MutableList<Request>,
    val context: Context,
    var mechanicName:String,
    var mechanicPhone:String,
) : RecyclerView.Adapter<ViewRequestAdapter.RequestViewHolder>(){

    class RequestViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvbusinessName: TextView
        val tvPhone : TextView
        val tvaddress : TextView
        val tvlocationlat: TextView
        val tvlocationlong: TextView
        val tvlocationlat1: TextView
        val tvlocationlat2: TextView
        val tvmechname: TextView
        val tvmechphone: TextView
        val token: TextView
        val ivdelete: Button
        val btnupdatemap: Button

        init {
            tvbusinessName=view.findViewById(R.id.tvbname)
            tvPhone= view.findViewById(R.id.tvBrand)
            tvaddress= view.findViewById(R.id.tvModel)
            tvlocationlat= view.findViewById(R.id.tvPlate)
            tvlocationlong= view.findViewById(R.id.tvAddress)
            tvlocationlat1=view.findViewById(R.id.tvLat)
            tvlocationlat2=view.findViewById(R.id.tvLong)
            tvmechname=view.findViewById(R.id.tvmechname)
            token=view.findViewById(R.id.tvToken)
            tvmechphone=view.findViewById(R.id.tvmechphone)
            ivdelete=view.findViewById(R.id.ivdelete)
            btnupdatemap=view.findViewById(R.id.btnupdatemap)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_request_layout, parent, false)

        return RequestViewHolder(view)

    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val blst = lstbusiness[position]
        holder.tvbusinessName.text = blst.problemtype
        holder.tvPhone.text = blst.vechbrand
        holder.tvaddress.text = blst.vechmodel
        holder.tvlocationlat.text = blst.vechplatenum
        holder.tvlocationlong.text = blst.address
        holder.tvlocationlat1.text = blst.lat
        holder.tvlocationlat2.text = blst.long
        holder.tvmechname.text = mechanicName
        holder.tvmechphone.text = mechanicPhone
        holder.token.text=blst.token



        holder.ivdelete.setOnClickListener {
            //for notification
            val title = holder.tvmechname.text.toString()
            val message = holder.tvmechphone.text.toString()
            val recipientToken = holder.token.text.toString()
            //for work history
            val problemtype= holder.tvbusinessName.text.toString()
            val address = holder.tvaddress.text.toString()
            val lat= holder.tvlocationlat.text.toString()
            val long= holder.tvlocationlat2.text.toString()
            val mechusername = holder.tvmechname.text.toString()
            val mechphone = holder.tvmechphone.text.toString()
            val clusername=blst.clusername.toString()

            val workHistory = workHistory(
                    problemtype = problemtype,
                    address = address,
                    lat = lat,
                    long = long,
                    clusername = clusername,
                    mechusername=mechusername,
                    mechphone = mechphone,
                    accepted = "true",
                    rejected = "false"


            )
            CoroutineScope(Dispatchers.IO).launch {
                try {
                   val workHistoryRepository=workHistoryRepository()
                    val response =  workHistoryRepository.insertWork(workHistory)
                    if(response.success == true){
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    context,
                                    "Added", Toast.LENGTH_SHORT
                            ).show()
                            lstbusiness.remove(blst)
                            notifyDataSetChanged()
                        }
                    }
                } catch (ex: java.lang.Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context,
                                ex.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message),
                    recipientToken
                ).also {
                    sendNotification(it)
                }
            }
        }

        holder.btnupdatemap.setOnClickListener {
            //for notification
            val title = holder.tvmechname.text.toString()
            val message = "Oops! Wait for another mech response"
            val recipientToken = holder.token.text.toString()

            if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                        NotificationData(title, message),
                        recipientToken
                ).also {
                    sendNotification(it)
                }
            }
            //for work history
            val problemtype= holder.tvbusinessName.text.toString()
            val address = holder.tvaddress.text.toString()
            val lat= holder.tvlocationlat.text.toString()
            val long= holder.tvlocationlat2.text.toString()
            val mechusername = holder.tvmechname.text.toString()
            val mechphone = holder.tvmechphone.text.toString()
            val clusername=blst.clusername.toString()

            val workHistory = workHistory(
                    problemtype = problemtype,
                    address = address,
                    lat = lat,
                    long = long,
                    clusername = clusername,
                    mechusername=mechusername,
                    mechphone = mechphone,
                    accepted = "false",
                    rejected = "true"


            )
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val workHistoryRepository=workHistoryRepository()
                    val response =  workHistoryRepository.insertWork(workHistory)
                    if(response.success == true){
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    context,
                                    "Canceled", Toast.LENGTH_SHORT
                            ).show()
                            lstbusiness.remove(blst)
                            notifyDataSetChanged()
                        }
                    }
                } catch (ex: java.lang.Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context,
                                ex.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }


        }


    }
    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d("debug", "Response: hello")
            } else {
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            Log.e("debug", e.toString())
        }
    }


    override fun getItemCount(): Int {
        return lstbusiness.size
    }

}