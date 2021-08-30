package com.afonsofrancof.connectdot.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.afonsofrancof.connectdot.MainActivity
import com.afonsofrancof.connectdot.R
import com.afonsofrancof.connectdot.objects.Chat
import com.afonsofrancof.connectdot.objects.Message
import com.afonsofrancof.connectdot.objects.User
import com.afonsofrancof.connectdot.utils.getUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class ChatAddListViewModel : ViewModel() {
    var userList = MutableLiveData<List<User>>()

    private val database =
        Firebase.database("https://connectdot-a66d2-default-rtdb.europe-west1.firebasedatabase.app")



    fun getUsers() {

        val myRef = database.reference.child("users")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.value =
                    dataSnapshot.children.map { requireNotNull(it.getValue(User::class.java)) }
                        .sortedBy { it.name }.filter{ it.userId != getUser().userId}
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.i("test", "test")
            }
        }
        myRef.addValueEventListener(postListener)
    }


}