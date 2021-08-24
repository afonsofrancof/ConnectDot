package com.afonsofrancof.connectdot.utils

import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*

@BindingAdapter("setImage")
fun ImageView.setImage(url : String?){
    this.isVisible = url!=null
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("timeAgo")
fun TextView.timeAgo(date : Date){
    val calendar = Calendar.getInstance()
    calendar.time = date

    this.text = DateUtils.getRelativeTimeSpanString(calendar.timeInMillis)

}