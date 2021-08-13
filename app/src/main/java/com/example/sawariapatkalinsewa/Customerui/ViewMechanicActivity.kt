package com.example.sawariapatkalinsewa.Customerui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.adapter.BusinessAdapter
import com.example.sawariapatkalinsewa.repository.BusinessRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewMechanicActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_mechanic)


        CoroutineScope(Dispatchers.IO).launch {
            recyclerview=findViewById(R.id.recyclerView)
            val repository=BusinessRepository()
            val response=repository.getBusiness(this@ViewMechanicActivity)
            withContext(Main){
                val adapter = BusinessAdapter(response,this@ViewMechanicActivity)
                recyclerview.layoutManager = LinearLayoutManager(this@ViewMechanicActivity)
                recyclerview.adapter=adapter
            }
        }
    }
    }


