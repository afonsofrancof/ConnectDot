package com.afonsofrancof.connectdot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.databinding.ChatAddElementBinding
import com.afonsofrancof.connectdot.objects.Chat
import com.afonsofrancof.connectdot.objects.User

class ChatAddListAdapter(private val onClickListener: OnClickListener) : ListAdapter<User, ChatAddListAdapter.ChatAddListViewHolder>(DiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatAddListViewHolder {
        return ChatAddListViewHolder(ChatAddElementBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ChatAddListAdapter.ChatAddListViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding.root.setOnClickListener {
            onClickListener.onClickUser(user)

        }
        holder.bind(user)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userId == newItem.userId
        }

    }

    class ChatAddListViewHolder(var binding: ChatAddElementBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user : User){
            binding.user = user
            binding.executePendingBindings()
        }
    }

    interface OnClickListener{
        fun onClickUser(user :User)
    }

}