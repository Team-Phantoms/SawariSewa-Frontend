package com.example.sawariapatkalinsewa.adapter

import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.Mechanicui.CustomerMapActivity
import com.example.sawariapatkalinsewa.Mechanicui.MechanicMapActivity
import com.example.sawariapatkalinsewa.Mechanicui.UpdateBusinessActivity
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.entity.clientWorkHistory
import com.example.sawariapatkalinsewa.entity.workHistory
import com.example.sawariapatkalinsewa.repository.clientworkHistoryRepository
import com.example.sawariapatkalinsewa.repository.workHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class MechHistoryAdapter(
    val lstwork: MutableList<clientWorkHistory>,
    val context: Context
) : RecyclerView.Adapter<MechHistoryAdapter.MechHistoryViewHolder>() {
    class MechHistoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvmechname: TextView
        val tvmphone : TextView
        val tvproblemtype : TextView
        val btnmap: TextView
        val tvmaddress: TextView
        val tvmlocationlat: TextView
        val tvmlocationlong: TextView
        val tvreject: Button

        init {
            tvmechname=view.findViewById(R.id.tvmechname)
            tvmphone= view.findViewById(R.id.tvmphone)
            tvproblemtype= view.findViewById(R.id.tvmproblemtype)
            tvmaddress= view.findViewById(R.id.tvmaddress)
            tvmlocationlat= view.findViewById(R.id.tvmlocationlat)
            tvmlocationlong= view.findViewById(R.id.tvmlocationlong)
            btnmap=view.findViewById(R.id.btnmap)
            tvreject=view.findViewById(R.id.tvreject)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MechHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_mechwork, parent, false)

        return MechHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MechHistoryViewHolder, position: Int) {
        val work=lstwork[position]
        Log.d("debug","value: $lstwork")
        val context=holder.itemView.context
        holder.tvmechname.text=work.clusername
        holder.tvmphone.text=work.contact
        holder.tvproblemtype.text=work.problemtype
        holder.tvmaddress.text=work.address
        holder.tvmlocationlat.text=work.lat
        holder.tvmlocationlong.text=work.long

        holder.btnmap.setOnClickListener {
            val latitude= holder.tvmlocationlat.text.toString()
            val longitude= holder.tvmlocationlong.text.toString()
            val intent=Intent(context, CustomerMapActivity::class.java)
            intent.putExtra("lat",latitude)
            intent.putExtra("long",longitude)
            context.startActivity(intent)
        }
        holder.tvreject.setOnClickListener {
val sms= SmsManager.getDefault()
            sms.sendTextMessage(holder.tvmphone.text.toString(),"ME","Your Request has been canceled by ${work.mechusername} Please find another mechanic",null,null)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val requestMechRepository = clientworkHistoryRepository()
                    val response = requestMechRepository.deleteRequest(work._id!!)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Request Deleted",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            ex.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            lstwork.remove(work)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return lstwork.size
    }

}