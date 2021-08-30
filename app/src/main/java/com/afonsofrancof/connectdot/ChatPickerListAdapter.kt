package com.afonsofrancof.connectdot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.databinding.ChatPickerElementBinding
import com.afonsofrancof.connectdot.objects.Chat

class ChatPickerListAdapter(private val onClickListener: OnClickListener) : ListAdapter<Chat, ChatPickerListAdapter.ChatListViewHolder>(DiffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatListViewHolder {
        return ChatListViewHolder(ChatPickerElementBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ChatPickerListAdapter.ChatListViewHolder, position: Int) {
        val chat = getItem(position)
        holder.bind(chat)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Chat>(){
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.chatId == newItem.chatId
        }

    }

    class ChatListViewHolder(var binding: ChatPickerElementBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chat : Chat){
            binding.chat = chat
            binding.executePendingBindings()
        }
    }

    interface OnClickListener{
        fun onLongPressChat(chat : Chat)
    }

}