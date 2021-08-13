package com.example.sawariapatkalinsewa.ui.slideshow

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.fragment.AddvehicleFragment
import com.example.sawariapatkalinsewa.fragment.ViewVehicleFragment

class SlideshowFragment : Fragment() {
    private lateinit var addvehicle:TextView
    private lateinit var viewvehicle:TextView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        addvehicle=root.findViewById(R.id.tvvehicleadd)
        viewvehicle=root.findViewById(R.id.tvvehicleview)

        addvehicle.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.linearContainer, AddvehicleFragment())
                addToBackStack(null)
                commit()
            }
        }

        viewvehicle.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.linearContainer, ViewVehicleFragment())
                addToBackStack(null)
                commit()
            }
        }


        return root
    }
}