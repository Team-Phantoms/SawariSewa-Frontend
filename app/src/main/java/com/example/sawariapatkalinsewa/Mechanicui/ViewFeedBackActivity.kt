package com.example.sawariapatkalinsewa.Mechanicui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.adapter.FeedBackAdapter
import com.example.sawariapatkalinsewa.repository.FeedBackRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewFeedBackActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_feed_back)

        CoroutineScope(Dispatchers.IO).launch {
            recyclerview=findViewById(R.id.recyclerView)
            val repository= FeedBackRepository()
            val response=repository.getfeedback().feed
            val responses=repository.getfeedback()
            if (responses.success==true){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@ViewFeedBackActivity, "Viewed", Toast.LENGTH_SHORT).show()
                }
            }


            withContext(Dispatchers.Main){
                val adapter = FeedBackAdapter(response,this@ViewFeedBackActivity)
                recyclerview.layoutManager = LinearLayoutManager(this@ViewFeedBackActivity)
                recyclerview.adapter=adapter
            }
        }
    }
}