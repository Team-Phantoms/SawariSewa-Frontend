package com.example.sawariapatkalinsewa.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.entity.Feedback

class FeedBackAdapter (
    val lstbusiness: MutableList<Feedback>,
    val context: Context
) : RecyclerView.Adapter<FeedBackAdapter.FeedBackViewHolder>(){

    class FeedBackViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvusername: TextView
        val tvuseremail : TextView
        val tvmessage : TextView



        init {
            tvusername=view.findViewById(R.id.tvname)
            tvuseremail= view.findViewById(R.id.tvemail)
            tvmessage= view.findViewById(R.id.tvmessage)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedBackViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_feedback_layout, parent, false)

        return FeedBackViewHolder(view)

    }

    override fun onBindViewHolder(holder: FeedBackViewHolder, position: Int) {
        val blst=lstbusiness[position]
        holder.tvusername.text=blst.clusername
        holder.tvuseremail.text=blst.clemail
        holder.tvmessage.text=blst.message




    }

    override fun getItemCount(): Int {
        return lstbusiness.size
    }
}