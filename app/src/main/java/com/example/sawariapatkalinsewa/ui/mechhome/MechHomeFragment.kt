package com.example.sawariapatkalinsewa.ui.mechhome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sawariapatkalinsewa.Mechanicui.AddBusinessActivity
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.Mechanicui.ViewBusinessActivity
import com.example.sawariapatkalinsewa.Mechanicui.ViewFeedBackActivity
import com.example.sawariapatkalinsewa.adapter.ViewRequestAdapter
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import com.example.sawariapatkalinsewa.repository.workHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MechHomeFragment : Fragment() {

 lateinit var addbusiness:CardView
 lateinit var updatebusiness:CardView
 lateinit var user:TextView
 lateinit var feedback:CardView
 lateinit var workHistory:CardView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_mechhome, container, false)
        addbusiness=root.findViewById(R.id.cvbusiness)
        updatebusiness=root.findViewById(R.id.cvupdate)
        feedback=root.findViewById(R.id.cvfeedback)
        user=root.findViewById(R.id.username)
        workHistory=root.findViewById(R.id.cvworkhistory)

        user.text="Welcome ${ServiceBuilder.username}"


        addbusiness.setOnClickListener {

            startActivity(
                Intent(
                    context, AddBusinessActivity::class.java
                )
            )
        }
        updatebusiness.setOnClickListener {
            startActivity(
                Intent(
                    context, ViewBusinessActivity::class.java
                )
            )
        }
        feedback.setOnClickListener {

            startActivity(
                Intent(
                    context, ViewFeedBackActivity::class.java
                )
            )
        }
        workHistory.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val repository= workHistoryRepository()
                val response=repository.getWork()
                val listrequest=response.wdata
                Log.d("requestdata", listrequest.toString())
            }
        }

        return root
    }

}
