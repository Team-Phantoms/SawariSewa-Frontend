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

class onBoardingFragment1 : Fragment() {
    lateinit var skip: TextView;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding1, container, false)
            skip=view.findViewById(R.id.tvskip)

        skip.setOnClickListener(object :View.OnClickListener {
            override fun onClick(view:View){
                val intent= Intent(this@onBoardingFragment1.context, LoginActivity::class.java)
                startActivity(intent)

            }
        })
        return view;
    }
}