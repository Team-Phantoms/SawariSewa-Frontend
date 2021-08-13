package com.example.sawariapatkalinsewa.Mechanicui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.adapter.ViewRequestAdapter
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewRequestActivity : AppCompatActivity() {
    private lateinit var recyclerview1: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_request)

        CoroutineScope(Dispatchers.IO).launch {
            recyclerview1=findViewById(R.id.recyclerView1)
            val repository= MechanicRepository()
            val response=repository.getRequest()
            val listrequest=response.requestdata
            Log.d("requestdata", listrequest.toString())
            withContext(Dispatchers.Main){
                val adapter = ViewRequestAdapter(listrequest,this@ViewRequestActivity)
                recyclerview1.layoutManager = LinearLayoutManager(this@ViewRequestActivity)
                recyclerview1.adapter=adapter
            }
        }
    }
}