package com.afonsofrancof.connectdot.utils

import android.text.format.DateUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.afonsofrancof.connectdot.R
import com.afonsofrancof.connectdot.objects.Message
import com.afonsofrancof.connectdot.objects.Post
import com.afonsofrancof.connectdot.objects.User
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import java.util.*

@BindingAdapter("setImage")
fun ImageView.setImage(url: String?) {
    this.isVisible = url != null
    Glide.with(this).load(url).placeholder(R.drawable.ic__image_placeholder).error(R.drawable.ic_error_image) .into(this)
}

@BindingAdapter("setProfilePicture")
fun ImageView.setProfilePicture(url : String?){
    Glide.with(this).load(url).placeholder(R.drawable.ic_avatar_placeholder).error(R.drawable.ic_avatar_placeholder).into(this)
}

@BindingAdapter("timeAgo")
fun TextView.timeAgo(date: Date) {
    val calendar = Calendar.getInstance()
    calendar.time = date

    this.text = DateUtils.getRelativeTimeSpanString(calendar.timeInMillis)
}

@BindingAdapter("likeLabel")
fun TextView.likeLabel(likedBy: MutableList<User>) {
    val nLikes = likedBy.size
    if (nLikes != 1) {
        this.text = "${nLikes} likes"
    } else {
        this.text = "1 like"
    }
}

@BindingAdapter("likedState")
fun ImageButton.likedState(likedBy: MutableList<User>) {
    this.isSelected = likedBy.any { it.userId == this.context.getFirebaseUser()?.uid ?: "" }
}

@BindingAdapter("repostsLabel")
fun TextView.reposts(post : Post) {
    this.isVisible = post.author.userId != this.context.getFirebaseUser()?.uid ?: ""
    this.text = post.repostedBy.size.toString()
}

@BindingAdapter("repostsState")
fun ImageButton.repostsState(post : Post) {
    if (post.repostedBy.any { it.userId == this.context.getFirebaseUser()?.uid ?: "" }){
        this.setImageResource(R.drawable.repost_icon_reposted)
    }else{
        this.setImageResource(R.drawable.repost_icon_not_reposted)
    }
    this.isVisible = post.author.userId != this.context.getFirebaseUser()?.uid ?: ""
    this.isEnabled = post.repostedBy.find{ it.userId == this.context.getFirebaseUser()?.uid ?: ""}==null
}

@BindingAdapter("isRepost")
fun TextView.isRepost(repost : Boolean){
    this.isVisible = repost
}

@BindingAdapter("deleteButton")
fun ImageButton.deleteButton(userId : String){
    this.isVisible = userId == this.context.getFirebaseUser()?.uid ?:""
}

// CHAT BINDING ADAPTERS

@BindingAdapter("setProfilePictureChat")
fun ImageView.setProfilePictureChat(userList : List<User>){
    val url = userList.filter { it.userId != getUser().userId }.first().pfpUrl
    Glide.with(this).load(url).placeholder(R.drawable.ic_avatar_placeholder).error(R.drawable.ic_avatar_placeholder).into(this)
}

@BindingAdapter("setUsernameChat")
fun TextView.setUsernameChat(userList: List<User>){
    this.text = userList.filter { it.userId != getUser().userId }.first().name

}

@BindingAdapter("setLastMessageChat")
fun TextView.setUsernameChat(lastMessage : Message){
    this.text = lastMessage.text
}