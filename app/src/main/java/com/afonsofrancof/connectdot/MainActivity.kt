package com.afonsofrancof.connectdot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.afonsofrancof.connectdot.databinding.ActivityMainBinding
import com.afonsofrancof.connectdot.objects.Chat
import com.afonsofrancof.connectdot.utils.getUser
import com.aghajari.zoomhelper.ZoomHelper
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        navController = this.findNavController(R.id.host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.bottomNavigation.isVisible = destination.id != R.id.postCreateFragment
            when (destination.id) {
                R.id.chatAddListFragment -> title = "Add a new chat"
                R.id.chatWindowFragment -> if (arguments != null) {
                    title =
                        (arguments.getParcelable<Chat>("chat"))?.userList?.first { it.userId != getUser().userId }?.name
                            ?: ""
                }
                R.id.ChatPickerListFragment -> title = "Pick a chat"
                R.id.profileFragment -> title = "Profile"
                R.id.feedFragment -> title = "Feed"
                R.id.settingsFragment -> title = "Settings"
                R.id.postCreateFragment -> title = "Create new post"
            }
        }


    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val zoomHelper = ZoomHelper.getInstance()
        zoomHelper.minScale = 1f
        zoomHelper.maxScale = 10f
        zoomHelper.dismissDuration = 200
        return zoomHelper.dispatchTouchEvent(ev!!,this) || super.dispatchTouchEvent(ev)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.settings, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        navController.navigate(R.id.settingsFragment)
//        return super.onOptionsItemSelected(item)
//    }

    fun newPost() {
        navController.navigate(R.id.postCreateFragment)
    }

    fun newChat() {
        navController.navigate(R.id.chatAddListFragment)
    }

//    fun goToChat(){
//        navController.navigate(R.id.chat)
//    }

    fun goBack() {
        navController.popBackStack()
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(Intent(this, LoginActivity::class.java)))
        finish()
    }
}