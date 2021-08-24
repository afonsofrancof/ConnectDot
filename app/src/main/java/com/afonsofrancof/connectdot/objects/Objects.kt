package com.afonsofrancof.connectdot.objects

import java.util.*

data class Post(
    var text: String? = null,
    var type: Int = 0,
    val date: Date = Date(),
    val edited: Boolean = false,
    val author : Author = Author(),
    var postId: String = "",
    var imgUrl: String? = null,
    val likes: Int = 0,
    val repost: Boolean = false,
    val reposts: Int = 0
)

data class Author(
    var name: String? = null, var authorId: String = "", var pfpUrl: String? = null
)