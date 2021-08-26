package com.afonsofrancof.connectdot.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afonsofrancof.connectdot.objects.Post
import com.afonsofrancof.connectdot.objects.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class ProfileViewModel : ViewModel() {

    var postList = MutableLiveData<List<Post>>()

    val like = MutableLiveData<Post>()
    private val database =
        Firebase.database("https://connectdot-a66d2-default-rtdb.europe-west1.firebasedatabase.app")

    fun getPosts(userId : String) {

        val myRef = database.reference.child("posts").orderByChild("author/userId").equalTo(userId)


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                postList.value =
                    dataSnapshot.children.map { requireNotNull(it.getValue(Post::class.java)) }
                        .sortedByDescending { it.date.time }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.i("test", "test")
            }
        }
        myRef.addValueEventListener(postListener)
    }

    fun addOrRemoveLike(post: Post, user: User): Int {
        if (post.likedBy.any { it.userId == user.userId }) {
            post.likedBy.removeAll { it.userId == user.userId }
            post.likes--

        } else {
            post.likedBy.add(user)
            post.likes++
        }
        database.reference.child("posts").child(post.postId).setValue(post)
//
        return post.likes
    }

    fun repost(post: Post, user: User) {

        val repostPost = Post()
        post.reposts++
        post.repostedBy.add(user)
        repostPost.text = post.text
        repostPost.imgUrl = post.imgUrl
        repostPost.originalPost = post.postId
        repostPost.repost = true
        repostPost.postId = user.userId + Calendar.getInstance().timeInMillis
        repostPost.author = user.copy()
        database.reference.child("posts").child(repostPost.postId).setValue(repostPost)
        database.reference.child("posts").child(post.postId).setValue(post)

    }

    fun removePost(post: Post, user: User) {
        if (user.userId == post.author.userId) {
            post.originalPost?.let {
                val originalPost: Post? =
                    postList.value?.find { it.postId == post.originalPost }?.copy()
                originalPost?.let {
                    originalPost.repostedBy.removeAll { it.userId == user.userId }
                    originalPost.reposts--
                    database.reference.child("posts").child(originalPost.postId)
                        .setValue(originalPost)
                }
            }
            database.reference.child("posts").child(post.postId).removeValue()
        }
    }

}