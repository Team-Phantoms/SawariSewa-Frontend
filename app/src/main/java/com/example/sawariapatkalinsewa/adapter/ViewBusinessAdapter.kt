package com.example.sawariapatkalinsewa.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import com.example.sawariapatkalinsewa.Mechanicui.UpdateBusinessActivity
import com.example.sawariapatkalinsewa.Mechanicui.UpdateMapActivity
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.repository.BusinessRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewBusinessAdapter (
    val lstbusiness: MutableList<Business>,
    val context: Context
) : RecyclerView.Adapter<ViewBusinessAdapter.VBusinessViewHolder>(){

    class VBusinessViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvname : TextView
        val tvphone : TextView
        val tvaddress : TextView
        val tvlocationlat: TextView
        val tvlocationlong: TextView
        val update: ImageView
        val delete: ImageView
        val updatemap: Button

        init {
            tvname= view.findViewById(R.id.tvbusinessName)
            tvphone= view.findViewById(R.id.tvPhone)
            tvaddress= view.findViewById(R.id.tvaddress)
            tvlocationlat= view.findViewById(R.id.tvlocationlat)
            tvlocationlong= view.findViewById(R.id.tvlocationlong)
            update=view.findViewById(R.id.ivupdatebusiness)
            delete=view.findViewById(R.id.ivdelete)
            updatemap=view.findViewById(R.id.btnupdatemap)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VBusinessViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_view_layout, parent, false)

        return VBusinessViewHolder(view)

    }

    override fun onBindViewHolder(holder: VBusinessViewHolder, position: Int) {
        val blst=lstbusiness[position]
        val context = holder.itemView.context
        holder.tvname.text=blst.fullname
        holder.tvphone.text=blst.phone
        holder.tvaddress.text=blst.address
        holder.tvlocationlat.text=blst.lat
        holder.tvlocationlong.text=blst.long

        holder.update.setOnClickListener{
            val intent = Intent(context, UpdateBusinessActivity::class.java)
            intent.putExtra("business", blst)
            context.startActivity(intent)
        }

        holder.delete.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete student")
            builder.setMessage("Are you sure you want to delete ${blst.fullname} ??")
            builder.setIcon(android.R.drawable.ic_delete)

            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val businessRepository = BusinessRepository()
                        val response = businessRepository.deleteBusiness(blst._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                lstbusiness.remove(blst)
                                Log.d("Business list size: ",lstbusiness.size.toString())
                                notifyDataSetChanged()
                                Toast.makeText(
                                    context,
                                    "Business Deleted",
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
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        holder.updatemap.setOnClickListener {
            val latitude= holder.tvlocationlat.text.toString()
            val longitude= holder.tvlocationlong.text.toString()
            val intent=Intent(context, UpdateMapActivity::class.java)
            intent.putExtra("lat",latitude)
            intent.putExtra("long",longitude)
            intent.putExtra("business", blst)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return lstbusiness.size
    }
}