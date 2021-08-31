package com.example.sawariapatkalinsewa.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.adapter.BusinessAdapter
import com.example.sawariapatkalinsewa.adapter.HistoryAdapter
import com.example.sawariapatkalinsewa.adapter.ViewBusinessAdapter
import com.example.sawariapatkalinsewa.repository.BusinessRepository
import com.example.sawariapatkalinsewa.repository.clientworkHistoryRepository
import com.example.sawariapatkalinsewa.repository.workHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryFragment : Fragment() {
    private lateinit var recyclerview: RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
//        btndriver=root.findViewById(R.id.btndriver)
//
//
//        btndriver.setOnClickListener{
//            CoroutineScope(Dispatchers.IO).launch {
//                val repository= workHistoryRepository()
//                val response=repository.getDriver()
//                val listrequest=response.rdata
//                Log.d("requestdata", listrequest.toString())
//            }
//        }
        CoroutineScope(Dispatchers.IO).launch {
            recyclerview=root.findViewById(R.id.recyclerView)
            val repository= clientworkHistoryRepository()
            val response=repository.getDriver()
            val listrequest=response.rdata
            withContext(Dispatchers.Main){
                val adapter = context?.let { HistoryAdapter(listrequest, it) }
                recyclerview.layoutManager = LinearLayoutManager(context)
                recyclerview.adapter=adapter
                if (adapter != null) {
                    adapter.notifyDataSetChanged()
                };

            }
        }
        return root
    }
}