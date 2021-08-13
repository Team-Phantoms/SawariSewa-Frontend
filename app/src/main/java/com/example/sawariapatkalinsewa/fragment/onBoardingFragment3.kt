package com.example.sawariapatkalinsewa.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sawariapatkalinsewa.LoginActivity
import com.example.sawariapatkalinsewa.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class onBoardingFragment3:Fragment() {
    lateinit var fab:FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding3, container, false)
        fab=view.findViewById(R.id.fab)

        fab.setOnClickListener(object :View.OnClickListener {
           override fun onClick(view:View){
                val intent=Intent(this@onBoardingFragment3.context,LoginActivity::class.java)
                startActivity(intent)

            }
        })



        return view;
    }
}