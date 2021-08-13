package com.example.sawariapatkalinsewa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter



class LoginPagerAdapter (private val lstfragmet:ArrayList<Fragment>,
                         fragmentManager:FragmentManager,
                         lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return lstfragmet.size
    }

    override fun createFragment(position: Int): Fragment {
        return lstfragmet[position]
    }

}

