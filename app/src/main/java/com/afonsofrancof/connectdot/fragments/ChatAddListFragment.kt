package com.afonsofrancof.connectdot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.ChatAddListAdapter
import com.afonsofrancof.connectdot.DividerItemDecoration
import com.afonsofrancof.connectdot.MainActivity
import com.afonsofrancof.connectdot.R
import com.afonsofrancof.connectdot.databinding.FragmentChatAddListBinding
import com.afonsofrancof.connectdot.objects.User
import com.afonsofrancof.connectdot.viewModels.ChatAddListViewModel


class ChatAddListFragment : Fragment() , ChatAddListAdapter.OnClickListener {

    private val viewModel: ChatAddListViewModel by lazy {
        ViewModelProvider(this).get(ChatAddListViewModel::class.java)
    }

    lateinit var binding: FragmentChatAddListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatAddListBinding.inflate(inflater)

        val adapter = ChatAddListAdapter(this)
        binding.chatList.addItemDecoration(DividerItemDecoration(30))
        binding.chatList.adapter = adapter
        binding.chatList.itemAnimator = Animator()
        viewModel.getUsers()
        viewModel.userList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            if ((binding.chatList.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition() < 2) {
                binding.chatList.smoothScrollToPosition(0)
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

    override fun onClickUser(user: User) {
        findNavController().navigate(ChatAddListFragmentDirections.actionChatAddListFragmentToChatPickerListFragment(user))
    }
}