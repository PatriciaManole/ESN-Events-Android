package com.example.esneventsandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.esneventsandroid.databinding.ActivityMainBinding
import com.example.esneventsandroid.fragments.EventsFragment
import com.example.esneventsandroid.fragments.HomeFragment
import com.example.esneventsandroid.fragments.NationalEventsFragment
import com.example.esneventsandroid.fragments.SplashFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(SplashFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.events -> replaceFragment(EventsFragment())
                R.id.profile -> replaceFragment(HomeFragment())
                R.id.natevents -> replaceFragment(NationalEventsFragment())

                else ->{



                }

            }

            true

        }
        binding.bottomNavigationView.visibility = View.GONE

    }

    fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}