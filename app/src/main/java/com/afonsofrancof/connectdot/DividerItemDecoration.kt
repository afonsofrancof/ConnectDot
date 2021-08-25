package com.afonsofrancof.connectdot


import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView


class DividerItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.left = spacing
        outRect.right = spacing
        outRect.top = spacing
        if(itemPosition+1== parent.adapter?.itemCount ?: 0){
            outRect.bottom = 240
        }else{
            outRect.bottom = 0
        }
    }
}
