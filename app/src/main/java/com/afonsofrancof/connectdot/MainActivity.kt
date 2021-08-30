package com.afonsofrancof.connectdot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.afonsofrancof.connectdot.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


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

    fun newChat(){
        navController.navigate(R.id.chatAddListFragment)
    }
    
//    fun goToChat(){
//        navController.navigate(R.id.chat)
//    }

    fun goBack(){
        navController.popBackStack()
    }

    fun signOut(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(Intent(this, LoginActivity::class.java)))
        finish()
    }
}