package com.afonsofrancof.connectdot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.databinding.ChatMessageIncomingBinding
import com.afonsofrancof.connectdot.databinding.ChatMessageOutgoingBinding
import com.afonsofrancof.connectdot.databinding.ChatPickerElementBinding
import com.afonsofrancof.connectdot.objects.Chat
import com.afonsofrancof.connectdot.objects.Message
import com.afonsofrancof.connectdot.utils.getUser

class ChatWindowAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Message,RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if(viewType==0) return OutgoingViewHolder(ChatMessageOutgoingBinding.inflate(LayoutInflater.from(parent.context)))
        return IncomingViewHolder(ChatMessageIncomingBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is OutgoingViewHolder) holder.bind(message)
        else if (holder is IncomingViewHolder) holder.bind(message)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.messageId == newItem.messageId
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position).userId == getUser().userId) return 0
        else return 1
    }

    class OutgoingViewHolder(var binding: ChatMessageOutgoingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.message = message
            binding.executePendingBindings()
        }
    }

    class IncomingViewHolder(var binding: ChatMessageIncomingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.message = message
            binding.executePendingBindings()
        }
    }

    interface OnClickListener {
        fun onLongPressChat(chat: Chat)
    }

}