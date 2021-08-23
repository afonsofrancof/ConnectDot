package com.afonsofrancof.connectdot

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.afonsofrancof.connectdot.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        navController = this.findNavController(R.id.host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation,navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.bottomNavigation.isVisible = destination.id!=R.id.postCreateFragment
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navController.navigate(R.id.settingsFragment)
        return super.onOptionsItemSelected(item)
    }

    fun newPost(){
        navController.navigate(R.id.postCreateFragment)
    }
}