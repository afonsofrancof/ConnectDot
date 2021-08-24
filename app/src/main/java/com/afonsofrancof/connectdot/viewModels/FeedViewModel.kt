package com.afonsofrancof.connectdot.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afonsofrancof.connectdot.objects.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FeedViewModel : ViewModel() {

    var postList = MutableLiveData<List<Post>>()

    fun getPosts() {

        val database =
            Firebase.database("https://connectdot-a66d2-default-rtdb.europe-west1.firebasedatabase.app")
        val myRef = database.reference.child("posts")


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val postListNew = mutableListOf<Post>()
                for (dataSnapshot in dataSnapshot.children) {
                    val post : Post? = dataSnapshot.getValue(Post::class.java)
                    post?.let {
                        postListNew.add(it)
                    }
                }
                postList.value = postListNew.reversed()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.i("test", "test")
            }
        }
        myRef.addValueEventListener(postListener)
    }
}