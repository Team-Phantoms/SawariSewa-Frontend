package com.example.sawariapatkalinsewa.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.repository.workHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {
    private lateinit var btndriver:Button
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        btndriver=root.findViewById(R.id.btndriver)


        btndriver.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val repository= workHistoryRepository()
                val response=repository.getDriver()
                val listrequest=response.rdata
                Log.d("requestdata", listrequest.toString())
            }
        }

        return root
    }
}