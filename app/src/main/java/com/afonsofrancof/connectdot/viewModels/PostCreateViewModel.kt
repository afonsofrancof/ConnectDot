package com.afonsofrancof.connectdot.viewModels

import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afonsofrancof.connectdot.objects.Post
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*

class PostCreateViewModel : ViewModel() {

    enum class Response { SUCCESS, FAIL }

    val result = MutableLiveData<Response>()

    lateinit var errorMessage: String

    fun submitPost(
        userid: String,
        username: String?,
        pfpUrl: String?,
        postText: String?,
        postImgUri: String?
    ) {
        val postId = userid + Calendar.getInstance().timeInMillis
        postImgUri?.let {
            val file = File(postImgUri)

            val filename = postId + "." + file.extension

            val imagesRef = FirebaseStorage.getInstance().reference.child("postImages/${filename}")
            val uploadTask = imagesRef.putFile(file.toUri())

            uploadTask.continueWithTask {
                imagesRef.downloadUrl
            }.addOnCompleteListener { task ->
                uploadPost(userid, username, pfpUrl, postText, task.result.toString(), postId)
            }.addOnFailureListener {
                errorMessage = it.localizedMessage ?: "Unknown Error"
                result.value = Response.FAIL
            }
        } ?: run {

            uploadPost(userid, username, pfpUrl, postText, null, postId)
        }

    }

    fun uploadPost(
        userid: String,
        username: String?,
        pfpUrl: String?,
        postText: String?,
        postImgUrl: String?,
        postId: String
    ) {
        val post = Post()
        post.author.userId = userid
        post.author.name = username
        post.author.pfpUrl = pfpUrl
        post.imgUrl = postImgUrl
        post.text = postText
        post.postId = postId
        val database =
            Firebase.database("https://connectdot-a66d2-default-rtdb.europe-west1.firebasedatabase.app")
        val myRef = database.reference
        myRef.child("posts").child(postId).setValue(post)
            .addOnSuccessListener { result.value = Response.SUCCESS }
            .addOnFailureListener {
                errorMessage = it.localizedMessage ?: "Unknown Error"
                result.value = Response.FAIL
            }
            .addOnCompleteListener { it.isSuccessful }
    }
}