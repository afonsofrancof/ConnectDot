package com.afonsofrancof.connectdot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.databinding.FeedPostBinding
import com.afonsofrancof.connectdot.objects.Post


class FeedAdapter(private val onClickListener: (Post) -> Unit) : ListAdapter<Post, FeedAdapter.FeedItemViewHolder>(DiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedItemViewHolder {
        return FeedItemViewHolder(FeedPostBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        val post = getItem(position)
        holder.binding.postImage.setOnClickListener {
            onClickListener(post)
        }
        holder.bind(post)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postId == newItem.postId
        }

    }

    class FeedItemViewHolder(var binding: FeedPostBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post){
            binding.post = post
            binding.executePendingBindings()
        }
    }


}