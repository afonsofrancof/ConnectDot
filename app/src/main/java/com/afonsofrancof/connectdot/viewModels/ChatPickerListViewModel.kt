package com.afonsofrancof.connectdot.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

class ChatPickerListViewModel : ViewModel() {

    var chatList = MutableLiveData<List<Chat>>()

    private val database =
        Firebase.database("https://connectdot-a66d2-default-rtdb.europe-west1.firebasedatabase.app")


    fun getChats() {

        val myRef = database.reference.child("chats")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chatList.value =
                    dataSnapshot.children.map { requireNotNull(it.getValue(Chat::class.java)) }
                        .sortedByDescending { it.lastMessage.timeStamp.time }.filter {
                            it.userList.contains(
                                getUser()
                            )
                        }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.i("test", "test")
            }
        }
        myRef.addValueEventListener(postListener)
    }


    fun createChat(user: User): Boolean {


        if (chatList.value?.any { chat -> ((chat.userList.size == 2) && (chat.userList.any { it.userId == getUser().userId }) && (chat.userList.any { it.userId == user.userId }))} == true) return true

        val chat = Chat()
        chat.userList.add(user)
        chat.userList.add(getUser())
        val chatId = getUser().userId + Calendar.getInstance().timeInMillis
        chat.chatId = chatId
        chat.lastMessage = Message()
        chat.lastMessage.text = ""
        chat.lastMessage.userId = ""
        database.reference.child("chats").child(chatId).setValue(chat)
        return false
    }

//    fun removePost(post: Post) {
//        val user = getUser()
//        if (user.userId == post.author.userId) {
//            post.originalPost?.let {
//                val originalPost: Post? =
//                    chatList.value?.find { it.postId == post.originalPost }?.copy()
//                originalPost?.let {
//                    originalPost.repostedBy.removeAll { it.userId == user.userId }
//                    originalPost.reposts--
//                    database.reference.child("posts").child(originalPost.postId)
//                        .setValue(originalPost)
//                }
//            }
//            database.reference.child("posts").child(post.postId).removeValue()
//        }
//    }

}