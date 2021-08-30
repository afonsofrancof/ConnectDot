package com.afonsofrancof.connectdot.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.ChatPickerListAdapter
import com.afonsofrancof.connectdot.DividerItemDecoration
import com.afonsofrancof.connectdot.MainActivity
import com.afonsofrancof.connectdot.databinding.FragmentChatPickerListBinding
import com.afonsofrancof.connectdot.objects.Chat
import com.afonsofrancof.connectdot.utils.getUser
import com.afonsofrancof.connectdot.viewModels.ChatPickerListViewModel
import java.util.*


class ChatPickerListFragment : Fragment(), ChatPickerListAdapter.OnClickListener {

    private val viewModel: ChatPickerListViewModel by lazy {
        ViewModelProvider(this).get(ChatPickerListViewModel::class.java)
    }

    val args : ChatPickerListFragmentArgs by navArgs()

    lateinit var binding: FragmentChatPickerListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatPickerListBinding.inflate(inflater)

        val adapter = ChatPickerListAdapter(this)
        binding.chatList.addItemDecoration(DividerItemDecoration(30))
        binding.chatList.adapter = adapter
        binding.chatList.itemAnimator = Animator()
        binding.newChatButton.setOnClickListener {
            (activity as MainActivity).newChat()
        }
        viewModel.getChats()
        viewModel.chatList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            if ((binding.chatList.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition() < 2) {
                binding.chatList.smoothScrollToPosition(0)
            }
            createChat()
        })

        return binding.root
    }


    class Animator() : DefaultItemAnimator() {
        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            dispatchAddFinished(holder)
            return true
        }

    }

    override fun onClickChat(chat: Chat) {
        findNavController().navigate(ChatPickerListFragmentDirections.actionChatPickerListFragmentToChatWindowFragment(chat))
    }

    fun createChat(){
        var alreadyExisted : Boolean = false
        args.user?.let { alreadyExisted = viewModel.createChat(it) }
        if(alreadyExisted){
            Log.i("test","testloool")
        }
    }
}