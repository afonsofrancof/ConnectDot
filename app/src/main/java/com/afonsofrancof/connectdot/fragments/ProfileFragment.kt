package com.afonsofrancof.connectdot.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.afonsofrancof.connectdot.DividerItemDecoration
import com.afonsofrancof.connectdot.FeedAdapter
import com.afonsofrancof.connectdot.databinding.FragmentProfileBinding
import com.afonsofrancof.connectdot.objects.Post
import com.afonsofrancof.connectdot.objects.User
import com.afonsofrancof.connectdot.utils.getUser
import com.afonsofrancof.connectdot.viewModels.ProfileViewModel

class ProfileFragment: Fragment(),FeedAdapter.OnClickListener {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)

        val adapter = FeedAdapter(this)
        binding.feed.addItemDecoration(DividerItemDecoration(30))
        binding.feed.adapter = adapter
        binding.feed.itemAnimator = FeedFragment.Animator()

        viewModel.getPosts(getUser().userId)

        viewModel.postList.observe(viewLifecycleOwner, Observer {
            //Falta FILTRAR POR ESTE USER SO
            adapter.submitList(it)
            Log.i("debug",
                (binding.feed.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
                    .toString()
            )
            if ((binding.feed.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition() < 2) {
                binding.feed.smoothScrollToPosition(0)
            }
        })



        return binding.root
    }

    override fun onClickLike(post: Post) {
        TODO("Not yet implemented")
    }

    override fun onClickRepost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun onClickUser(author: User) {
        TODO("Not yet implemented")
    }

    override fun onClickImage(post: Post) {
        TODO("Not yet implemented")
    }

    override fun onClickDelete(post: Post) {
        TODO("Not yet implemented")
    }
}