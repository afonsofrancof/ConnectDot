package com.afonsofrancof.connectdot.objects

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

data class Post(
    var text: String? = null,
    val date: Date = Date(),
    val edited: Boolean = false,
    var author : User = User(),
    var postId: String = "",
    var imgUrl: String? = null,
    var likes: Int = 0,
    var repost: Boolean = false,
    var reposts: Int = 0,
    val repostedBy : MutableList<User> = mutableListOf(),
    var originalPost : String? = null,
    val likedBy : MutableList<User> = mutableListOf()
)

@Parcelize data class User(
    var name: String? = null, var userId: String = "", var pfpUrl: String? = null
) : Parcelable

@Parcelize data class Message(
    var messageId : String = "",
    var userId : String = "",
    var text: String? = null,
    var photoUrl : String? = null,
    var timeStamp : Date = Date()
) : Parcelable


@Parcelize data class Chat(
    var chatId : String = "",
    var userList : MutableList<User> = mutableListOf(),
    var messages : MutableList<Message> = mutableListOf(),
    var lastMessage : Message? = Message()
) : Parcelable