package com.afonsofrancof.connectdot.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun Context.getUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser

fun Context.isUserLoggedIn(): Boolean = getUser()!=null