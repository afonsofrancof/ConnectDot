package com.afonsofrancof.connectdot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.afonsofrancof.connectdot.databinding.FragmentFeedBinding
import com.afonsofrancof.connectdot.databinding.FragmentProfileBinding

class ProfileFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater)



        return binding.root
    }
}