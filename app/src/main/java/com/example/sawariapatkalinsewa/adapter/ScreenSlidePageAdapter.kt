package com.example.sawariapatkalinsewa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sawariapatkalinsewa.fragment.onBoardingFragment1
import com.example.sawariapatkalinsewa.fragment.onBoardingFragment2
import com.example.sawariapatkalinsewa.fragment.onBoardingFragment3

class ScreenSlidePageAdapter (fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> onBoardingFragment1()
            1 -> onBoardingFragment2()
            2 -> onBoardingFragment3()

            else -> onBoardingFragment1()
        }
    }

    override fun getCount(): Int {
       return 3;
    }
}