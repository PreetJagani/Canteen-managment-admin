package com.canteenManagment.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.canteenManagment.admin.Fragments.HomeFragment
import com.canteenManagment.admin.Fragments.MenuFragment
import com.canteenManagment.admin.Fragments.ProfileFragment
import com.canteenManagment.admin.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        setContentView(binding.root)

        openFragment(HomeFragment())
        binding.chipNavigationBar.setItemSelected(R.id.home)


        binding.chipNavigationBar.setOnItemSelectedListener {

            when(it){
                R.id.home -> openFragment(HomeFragment())
                R.id.menu -> openFragment(MenuFragment())
                R.id.profile -> openFragment(ProfileFragment())
            }
        }

    }

    private fun openFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }
}