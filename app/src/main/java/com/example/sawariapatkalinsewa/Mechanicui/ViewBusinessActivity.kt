package com.example.sawariapatkalinsewa.Mechanicui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.adapter.ViewBusinessAdapter
import com.example.sawariapatkalinsewa.repository.BusinessRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewBusinessActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_business)

        CoroutineScope(Dispatchers.IO).launch {
            recyclerview=findViewById(R.id.recyclerView)
            val repository= BusinessRepository()
            val response=repository.getmechBusiness(this@ViewBusinessActivity)

            withContext(Dispatchers.Main){
                val adapter = ViewBusinessAdapter(response,this@ViewBusinessActivity)
                recyclerview.layoutManager = LinearLayoutManager(this@ViewBusinessActivity)
                recyclerview.adapter=adapter
                adapter.notifyDataSetChanged();

            }
        }




    }
}