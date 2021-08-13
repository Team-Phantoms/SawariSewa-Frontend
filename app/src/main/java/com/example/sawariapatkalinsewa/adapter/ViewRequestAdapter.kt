package com.example.sawariapatkalinsewa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.entity.Request


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

    }

    override fun getItemCount(): Int {
        return lstbusiness.size
    }

}