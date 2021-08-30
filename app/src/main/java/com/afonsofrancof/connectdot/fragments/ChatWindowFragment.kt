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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.ChatAddListAdapter
import com.afonsofrancof.connectdot.ChatWindowAdapter
import com.afonsofrancof.connectdot.DividerItemDecoration
import com.afonsofrancof.connectdot.databinding.FragmentChatAddListBinding
import com.afonsofrancof.connectdot.databinding.FragmentChatWindowBinding
import com.afonsofrancof.connectdot.objects.Chat
import com.afonsofrancof.connectdot.objects.User
import com.afonsofrancof.connectdot.viewModels.ChatAddListViewModel
import com.afonsofrancof.connectdot.viewModels.ChatWindowViewModel

class ChatWindowFragment : Fragment(),ChatWindowAdapter.OnClickListener {

    private val viewModel: ChatWindowViewModel by lazy {
        ViewModelProvider(this).get(ChatWindowViewModel::class.java)
    }

    val args : ChatWindowFragmentArgs by navArgs()

    lateinit var binding: FragmentChatWindowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getMessages(args.chatId)
        binding = FragmentChatWindowBinding.inflate(inflater)

        val adapter = ChatWindowAdapter(this)
        binding.messageRecyclerView.addItemDecoration(DividerItemDecoration(30))
        binding.messageRecyclerView.adapter = adapter
        binding.messageRecyclerView.layoutManager = GridLayoutManager(requireContext(),1,RecyclerView.VERTICAL,true)
        binding.messageRecyclerView.itemAnimator = Animator()
//        binding.textInput.doOnTextChanged { text, _, _, _ ->
//            viewModel.messageText = text.toString()
//        } FALTA FAZER O BUTAO FICAR DISABLED
        binding.sendMessageButton.setOnClickListener {
            viewModel.createMessage(binding.textInput.text.toString())
        }
        viewModel.chat.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.messages)
            if ((binding.messageRecyclerView.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition() < 2) {
                binding.messageRecyclerView.smoothScrollToPosition(0)
            }
        })
        return binding.root
    }


    class Animator() : DefaultItemAnimator() {
        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            dispatchAddFinished(holder)
            return true
        }

    }

    override fun onLongPressChat(chat: Chat) {
        Toast.makeText(requireContext(),"Not Yet Implemented",Toast.LENGTH_SHORT).show()
    }
}