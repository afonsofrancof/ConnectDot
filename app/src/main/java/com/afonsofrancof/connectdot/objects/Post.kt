package com.afonsofrancof.connectdot.objects

import java.util.*

data class Post(var title: String
                ,var text:String?
                ,var type : Int
                ,val date : Date
                ,val edited :Boolean
                ,val authorId : String
                ,val postId : String
                ,val imgUrl: String?
                ,val likes: Int
                ,val reposts : Int)
