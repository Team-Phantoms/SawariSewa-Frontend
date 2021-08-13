package com.example.sawariapatkalinsewa.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.*
import com.example.sawariapatkalinsewa.Customerui.FeedbackActivity
import com.example.sawariapatkalinsewa.Mechanicui.MechanicMapActivity
import com.example.sawariapatkalinsewa.entity.Business


class BusinessAdapter(
    val lstbusiness: MutableList<Business>,
    val context: Context
) : RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>(){

    class BusinessViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvuser:TextView
        val tvname : TextView
        val tvphone : TextView
        val tvaddress : TextView
        val tvlocationlat:TextView
        val tvlocationlong:TextView
        val showmore:TextView
        val btncall:TextView
        val btnmap:TextView
        val btnfeedback:TextView

        init {
            tvuser=view.findViewById(R.id.tvusername)
            tvname= view.findViewById(R.id.tvbusinessName)
            tvphone= view.findViewById(R.id.tvPhone)
            tvaddress= view.findViewById(R.id.tvaddress)
            tvlocationlat= view.findViewById(R.id.tvlocationlat)
            tvlocationlong= view.findViewById(R.id.tvlocationlong)
            showmore=view.findViewById(R.id.showmore)
            btncall=view.findViewById(R.id.btncall)
            btnmap=view.findViewById(R.id.btnmap)
            btnfeedback=view.findViewById(R.id.btnFeedback)

            tvlocationlong.visibility=View.GONE
            tvlocationlat.visibility=View.GONE
            btnmap.visibility=View.GONE

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_layout, parent, false)

        return BusinessViewHolder(view)

    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
       val blst=lstbusiness[position]
        val context = holder.itemView.context
        holder.tvuser.text=blst.mechusername
        holder.tvname.text=blst.fullname
        holder.tvphone.text=blst.phone
        holder.tvaddress.text=blst.address
        holder.showmore.setTag(1);
        holder.showmore.setOnClickListener{
            val status = holder.itemView.getTag()
            if (status==1) {
                holder.btnmap.isVisible=true
                holder.tvlocationlong.isVisible = true
                holder.tvlocationlong.text = blst.long
                holder.tvlocationlat.isVisible = true
                holder.tvlocationlat.text = blst.lat
                holder.showmore.setText("ShowLess")
                holder.itemView.setTag(0)

            }
            else if (status==0){
                holder.tvlocationlong.visibility=View.GONE
                holder.tvlocationlat.visibility=View.GONE
                holder.showmore.setText("Show More")
                holder.itemView.setTag(1)
            }
            else{
                holder.showmore.setText("Show More")
                holder.itemView.setTag(1)
            }
        }

        holder.btncall.setOnClickListener {

            val intent=Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + holder.tvphone.text.toString()));
            context.startActivity(intent)
        }

        holder.btnmap.setOnClickListener {
            val latitude= holder.tvlocationlat.text.toString()
            val longitude= holder.tvlocationlong.text.toString()
            val user=holder.tvname.text.toString()
            val intent=Intent(context, MechanicMapActivity::class.java)
            intent.putExtra("lat",latitude)
            intent.putExtra("long",longitude)
            intent.putExtra("user",user)
            context.startActivity(intent)
        }

        holder.btnfeedback.setOnClickListener{
            val intent = Intent(context, FeedbackActivity::class.java)
            intent.putExtra("feedback", blst)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return lstbusiness.size
    }
}

