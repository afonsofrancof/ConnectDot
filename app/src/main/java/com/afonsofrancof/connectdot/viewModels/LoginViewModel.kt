package com.afonsofrancof.connectdot.viewModels

import androidx.lifecycle.ViewModel
import com.afonsofrancof.connectdot.objects.User
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    fun addUserToDb(user : User){
        val database =
            Firebase.database("https://connectdot-a66d2-default-rtdb.europe-west1.firebasedatabase.app")

        database.reference.child("users").child(user.userId).setValue(user)
    }

}