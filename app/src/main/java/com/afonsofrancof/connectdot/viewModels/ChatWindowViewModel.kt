package com.afonsofrancof.connectdot.viewModels

import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
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

class ChatWindowViewModel : ViewModel() {

    var chat = MutableLiveData<Chat>()

    val database =
        Firebase.database("https://connectdot-a66d2-default-rtdb.europe-west1.firebasedatabase.app")


    var imgUri : String? = null

    fun getMessages(chatId: String) {
        val myRef = database.reference.child("chats").child(chatId)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chat.value = dataSnapshot.getValue(Chat::class.java)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.i("test", "test")
            }
        }
        myRef.addValueEventListener(postListener)
    }

    fun createMessage(text: String) {
        val newChat = requireNotNull(chat.value).copy()
        val newMessage = Message()
        newMessage.userId = getUser().userId
        newMessage.text = text.trim { it == ' ' }
        newMessage.messageId = getUser().userId + Calendar.getInstance().timeInMillis
        newMessage.photoUrl = imgUri
        newChat.lastMessage = newMessage
        newChat.messages.add(newMessage)
        database.reference.child("chats").child(chat.value!!.chatId).setValue(newChat)
    }



}