package com.example.sawariapatkalinsewa.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.Mechanicui.CustomerMapActivity
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.entity.clientWorkHistory
import com.example.sawariapatkalinsewa.entity.workHistory

class MechWorkHistoryAdapter (
    val lstwork: MutableList<workHistory>,
    val context: Context
) : RecyclerView.Adapter<MechWorkHistoryAdapter.MechHistoryViewHolder>() {
    class MechHistoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvmechname: TextView
        val tvmphone : TextView
        val tvproblemtype : TextView
        val tvmaddress: TextView
        val tvmlocationlat: TextView
        val tvmlocationlong: TextView


        init {
            tvmechname=view.findViewById(R.id.tvmechname)
            tvmphone= view.findViewById(R.id.tvmphone)
            tvproblemtype= view.findViewById(R.id.tvmproblemtype)
            tvmaddress= view.findViewById(R.id.tvmaddress)
            tvmlocationlat= view.findViewById(R.id.tvmlocationlat)
            tvmlocationlong= view.findViewById(R.id.tvmlocationlong)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MechHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_workhistory, parent, false)

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

    }

    override fun getItemCount(): Int {
        return lstwork.size
    }

}