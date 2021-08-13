package com.example.sawariapatkalinsewa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.sawariapatkalinsewa.adapter.LoginPagerAdapter
import com.example.sawariapatkalinsewa.fragment.LoginTabFragment
import com.example.sawariapatkalinsewa.fragment.SignupTabFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LoginActivity : AppCompatActivity() {
   private lateinit var tabLayout:TabLayout
   private lateinit var viewPager: ViewPager2
   private lateinit var fb: FloatingActionButton
   private lateinit var google: FloatingActionButton
   private lateinit var twitter: FloatingActionButton
    private lateinit var lstTitle:ArrayList<String>
    private lateinit var lstFragment:ArrayList<Fragment>
    val v=0f;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tabLayout=findViewById(R.id.tab_layout)
        viewPager=findViewById(R.id.view_pager)
        fb=findViewById(R.id.fab_fb)
        google=findViewById(R.id.fab_google)
        twitter=findViewById(R.id.fab_twitter)

        populateList()
        val adapter= LoginPagerAdapter(lstFragment, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager){tab, position ->
            tab.text =lstTitle[position]
        }.attach()



        fb.setTranslationY(300f)
        google.setTranslationY(300f)
        twitter.setTranslationY(300f)
        tabLayout.setTranslationY(300f)


        fb.setAlpha(v);
        google.setAlpha(v);
        twitter.setAlpha(v);
        tabLayout.setAlpha(v);

        fb.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(400).start()
        google.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(600).start()
        twitter.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(800).start()
        tabLayout.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(100).start()





    }

    private fun populateList(){
        lstTitle= ArrayList<String>()
        lstTitle.add("Login")
        lstTitle.add("SignUp")
        lstFragment= ArrayList<Fragment>()
        lstFragment.add(LoginTabFragment())
        lstFragment.add(SignupTabFragment())
    }

}