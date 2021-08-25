package com.afonsofrancof.connectdot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.databinding.FeedPostBinding
import com.afonsofrancof.connectdot.objects.User
import com.afonsofrancof.connectdot.objects.Post


class FeedAdapter(private val onClickListener: OnClickListener) : ListAdapter<Post, FeedAdapter.FeedItemViewHolder>(DiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedItemViewHolder {
        return FeedItemViewHolder(FeedPostBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        val post = getItem(position)
        holder.binding.postImage.setOnClickListener {
            onClickListener.onClickImage(post)
        }
        holder.binding.likeButton.setOnClickListener {
            onClickListener.onClickLike(post)
        }
        holder.binding.repostButton.setOnClickListener {
            onClickListener.onClickRepost(post)
        }
        holder.binding.profilePicture.setOnClickListener {
            onClickListener.onClickUser(post.author)
        }
        holder.binding.username.setOnClickListener {
            onClickListener.onClickUser(post.author)
        }
        holder.binding.deletePostButton.setOnClickListener {
            onClickListener.onClickDelete(post)
        }
        holder.bind(post)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postId == newItem.postId && oldItem.likedBy.count() == newItem.likedBy.count()
        }

    }

    class FeedItemViewHolder(var binding: FeedPostBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post){
            binding.post = post
            binding.executePendingBindings()
        }
    }

    interface OnClickListener{
        fun onClickLike(post: Post)
        fun onClickRepost(post: Post)
        fun onClickUser(author: User)
        fun onClickImage(post:Post)
        fun onClickDelete(post:Post)
    }

}