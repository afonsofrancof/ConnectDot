package com.afonsofrancof.connectdot.objects

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

data class User(
    var name: String? = null, var userId: String = "", var pfpUrl: String? = null
)