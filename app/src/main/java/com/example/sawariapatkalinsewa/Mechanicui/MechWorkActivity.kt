package com.example.sawariapatkalinsewa.Mechanicui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.adapter.MechHistoryAdapter
import com.example.sawariapatkalinsewa.repository.clientworkHistoryRepository
import com.example.sawariapatkalinsewa.repository.workHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MechWorkActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mech_work)

        CoroutineScope(Dispatchers.IO).launch {
            recyclerview=findViewById(R.id.recyclerView)
            val repository= clientworkHistoryRepository()
            val response=repository.getClient()
            val listrequest=response.mwdata
            Log.d("requestdata", listrequest.toString())
            withContext(Dispatchers.Main){
                val adapter = MechHistoryAdapter(listrequest,this@MechWorkActivity)
                recyclerview.layoutManager = LinearLayoutManager(this@MechWorkActivity)
                recyclerview.adapter=adapter
                adapter.notifyDataSetChanged();

            }

        }
    }
}