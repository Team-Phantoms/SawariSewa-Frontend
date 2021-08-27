package com.example.sawariapatkalinsewa.Mechanicui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.adapter.MechHistoryAdapter
import com.example.sawariapatkalinsewa.adapter.MechWorkHistoryAdapter
import com.example.sawariapatkalinsewa.repository.clientworkHistoryRepository
import com.example.sawariapatkalinsewa.repository.workHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MechWorkHistoryActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mech_work_history)

        CoroutineScope(Dispatchers.IO).launch {
            recyclerview=findViewById(R.id.recyclerView)
            val repository= workHistoryRepository()
            val response=repository.getWork()
            val listrequest=response.wdata
            Log.d("requestdata", listrequest.toString())
            withContext(Dispatchers.Main){
                val adapter = MechWorkHistoryAdapter(listrequest,this@MechWorkHistoryActivity)
                recyclerview.layoutManager = LinearLayoutManager(this@MechWorkHistoryActivity)
                recyclerview.adapter=adapter
                adapter.notifyDataSetChanged();

            }

        }
    }
}