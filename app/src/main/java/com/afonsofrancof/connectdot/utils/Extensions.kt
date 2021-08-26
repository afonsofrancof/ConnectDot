package com.afonsofrancof.connectdot.utils

import android.content.Context
import com.afonsofrancof.connectdot.objects.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun Any.getFirebaseUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser

fun Any.isUserLoggedIn(): Boolean = getFirebaseUser() != null

fun FirebaseUser.toUser(): User = User(this.displayName, this.uid, this.photoUrl.toString())

fun Any.getUser() : User = requireNotNull(getFirebaseUser()?.toUser())