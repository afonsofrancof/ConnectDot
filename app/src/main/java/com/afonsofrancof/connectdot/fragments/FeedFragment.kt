package com.afonsofrancof.connectdot.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afonsofrancof.connectdot.DividerItemDecoration
import com.afonsofrancof.connectdot.FeedAdapter
import com.afonsofrancof.connectdot.MainActivity
import com.afonsofrancof.connectdot.databinding.FragmentFeedBinding
import com.afonsofrancof.connectdot.objects.Post
import com.afonsofrancof.connectdot.objects.User
import com.afonsofrancof.connectdot.utils.getUser
import com.afonsofrancof.connectdot.viewModels.FeedViewModel
import com.google.android.material.internal.NavigationMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class FeedFragment : Fragment(), FeedAdapter.OnClickListener {

    private val viewModel: FeedViewModel by lazy {
        ViewModelProvider(this).get(FeedViewModel::class.java)
    }

    lateinit var binding: FragmentFeedBinding

    val args by navArgs<FeedFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater)

        binding.postAddButton.setOnClickListener { (activity as MainActivity).newPost() }
        val adapter = FeedAdapter(this)
        binding.feed.addItemDecoration(DividerItemDecoration(30))
        binding.feed.adapter = adapter
        binding.feed.itemAnimator = Animator()
        viewModel.getPosts(!args.isFeed)
        binding.user = getUser()
        viewModel.postList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            if ((binding.feed.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition() < 2) {
                binding.feed.smoothScrollToPosition(0)
            }
        })
        binding.logoutButton.setOnClickListener {
            (activity as MainActivity).signOut()
        }
        binding.userHeader.isVisible = !args.isFeed
        binding.postAddButton.isVisible = args.isFeed


        return binding.root
    }

    override fun onClickLike(post: Post) {

        viewModel.addOrRemoveLike(post.copy())

    }

    override fun onClickRepost(post: Post) {
        viewModel.repost(post.copy())

    }

    override fun onClickUser(author: User) {
        TODO("Not yet implemented")
    }

    override fun onClickImage(post: Post) {
        TODO("Not yet implemented")
    }

    override fun onClickDelete(post: Post) {

        viewModel.removePost(post.copy())
    }

    fun updateLikesLabel(likes: Int) {

    }

    class Animator() : DefaultItemAnimator() {
        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            dispatchAddFinished(holder)
            return true
        }

    }
}