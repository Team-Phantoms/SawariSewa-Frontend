package com.example.sawariapatkalinsewa.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.entity.clientWorkHistory
import com.example.sawariapatkalinsewa.entity.workHistory
import com.example.sawariapatkalinsewa.repository.RequestMechRepository
import com.example.sawariapatkalinsewa.repository.clientworkHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryAdapter(
        val lstwork: MutableList<clientWorkHistory>,
        val context: Context
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val tvmechname: TextView
        val tvmphone : TextView
        val tvproblemtype : TextView
        val btncall: TextView
        val btnaccept: Button

        init {
            tvmechname=view.findViewById(R.id.tvmechname)
            tvmphone= view.findViewById(R.id.tvmphone)
            tvproblemtype= view.findViewById(R.id.tvmproblemtype)
            btncall=view.findViewById(R.id.btncall)
            btnaccept=view.findViewById(R.id.btnaccept)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_work, parent, false)

        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        var work=lstwork[position]
        val context=holder.itemView.context
        holder.tvmechname.text=work.mechusername
        holder.tvmphone.text=work.mechphone
        holder.tvproblemtype.text=work.problemtype
        holder.btncall.setOnClickListener {
            val intent= Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + holder.tvmphone.text.toString()));
            context.startActivity(intent)
        }
        holder.btnaccept.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val requestMechRepository = clientworkHistoryRepository()
                    val response = requestMechRepository.deleteRequest(work._id!!)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
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
            lstwork.remove(work)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return lstwork.size
    }

}