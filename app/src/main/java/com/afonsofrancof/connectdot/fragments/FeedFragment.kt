package com.afonsofrancof.connectdot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.afonsofrancof.connectdot.*
import com.afonsofrancof.connectdot.databinding.FragmentFeedBinding
import com.afonsofrancof.connectdot.viewModels.FeedViewModel
import com.afonsofrancof.connectdot.viewModels.PostCreateViewModel

class FeedFragment: Fragment() {

    private val viewModel: FeedViewModel by lazy {
        ViewModelProvider(this).get(FeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater)

        binding.postAddButton.setOnClickListener { (activity as MainActivity).newPost() }
        val adapter = FeedAdapter {
        }
        binding.feed.addItemDecoration(DividerItemDecoration(30))
        binding.feed.adapter = adapter
        viewModel.getPosts()

        viewModel.postList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })



        return binding.root
    }
}