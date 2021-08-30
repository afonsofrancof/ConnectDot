package com.afonsofrancof.connectdot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.ChatWindowAdapter
import com.afonsofrancof.connectdot.databinding.FragmentChatWindowBinding
import com.afonsofrancof.connectdot.objects.Chat
import com.afonsofrancof.connectdot.utils.getUser
import com.afonsofrancof.connectdot.viewModels.ChatWindowViewModel

class ChatWindowFragment : Fragment(), ChatWindowAdapter.OnClickListener {

    private val viewModel: ChatWindowViewModel by lazy {
        ViewModelProvider(this).get(ChatWindowViewModel::class.java)
    }

    val args: ChatWindowFragmentArgs by navArgs()

    lateinit var binding: FragmentChatWindowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getMessages(args.chat.chatId)
        binding = FragmentChatWindowBinding.inflate(inflater)

        val adapter = ChatWindowAdapter(this)
        binding.chat = args.chat
        binding.sendMessageButton.isEnabled = false
        binding.messageRecyclerView.adapter = adapter
        binding.messageRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, true)
        binding.messageRecyclerView.itemAnimator = Animator()
        binding.textInput.doOnTextChanged { text, _, _, _ ->
            binding.sendMessageButton.isEnabled = !text.isNullOrBlank()

        }
        binding.sendMessageButton.setOnClickListener {
            viewModel.createMessage(binding.textInput.text.toString())
        }
        viewModel.chat.observe(viewLifecycleOwner, Observer {
            val messages = it.messages.sortedBy { msg -> msg.timeStamp.time }.reversed()
            adapter.submitList(messages)
            if ((binding.messageRecyclerView.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition() < 3) {
                binding.messageRecyclerView.smoothScrollToPosition(0)
            }
            if(messages.isNotEmpty())
            if (messages.first().userId == getUser().userId) {
                binding.textInput.text = null
                binding.messageRecyclerView.smoothScrollToPosition(0)
            }
        })
        binding.messageRecyclerView.addOnScrollListener(ScrollListener(binding))
        binding.scrollToBottom.setOnClickListener {
            binding.messageRecyclerView.smoothScrollToPosition(
                0
            )
        }
        return binding.root
    }

    class ScrollListener(val binding: FragmentChatWindowBinding) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if ((binding.messageRecyclerView.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition() > 0) {
                binding.scrollToBottom.visibility = View.VISIBLE
            } else {
                binding.scrollToBottom.visibility = View.GONE
            }
        }
    }

    class Animator() : DefaultItemAnimator() {
        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            dispatchAddFinished(holder)
            return true
        }

    }

    override fun onLongPressChat(chat: Chat) {
        Toast.makeText(requireContext(), "Not Yet Implemented", Toast.LENGTH_SHORT).show()
    }
}