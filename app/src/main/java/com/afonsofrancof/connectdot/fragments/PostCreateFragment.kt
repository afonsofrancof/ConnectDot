package com.afonsofrancof.connectdot.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.afonsofrancof.connectdot.MainActivity
import com.afonsofrancof.connectdot.R
import com.afonsofrancof.connectdot.databinding.FragmentPostCreateBinding
import com.afonsofrancof.connectdot.utils.getFirebaseUser
import com.afonsofrancof.connectdot.viewModels.PostCreateViewModel
import com.bumptech.glide.Glide
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import java.util.*


class PostCreateFragment : Fragment() {

    lateinit var binding: FragmentPostCreateBinding

    lateinit var easyImage: EasyImage

    var imgUri: String? = null
        set(value) {
            field = value
            binding.postImageContainer.isVisible = value != null
            binding.addImageButton.isVisible = value == null
            if (value != null) {
                Glide.with(requireContext())
                    .load(field)
                    .into(binding.postPicture)
            }
        }

    private val viewModel: PostCreateViewModel by lazy {
        ViewModelProvider(this).get(PostCreateViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentPostCreateBinding.inflate(inflater)
        imgUri = null
        easyImage = EasyImage.Builder(requireContext())
            .allowMultiple(false)
            .build()
        binding.addImageButton.setOnClickListener {
            easyImage.openCameraForImage(this)
        }
        binding.changePfp.setOnClickListener { imgUri = null }
        viewModel.result.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.publishButton.isEnabled = true
            binding.publishButton.setIconResource(R.drawable.send_icon)
            binding.progressBarPublish.isVisible = false
            if (it == PostCreateViewModel.Response.FAIL) Toast.makeText(
                requireContext(),
                viewModel.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            else {
                (activity as MainActivity).goBack()
            }

        })


        binding.publishButton.setOnClickListener {
            val postText = binding.postText.text
            if (imgUri == null && postText.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Your post needs either a picture , text or both",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            binding.publishButton.setIconResource(R.drawable.empty)
            binding.progressBarPublish.isVisible = true
            binding.publishButton.isEnabled = false
            getFirebaseUser()?.let { user ->
                viewModel.submitPost(
                    user.uid,
                    user.displayName,
                    user.photoUrl.toString(),
                    postText.toString(),
                    imgUri
                )

            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            activity as Activity,
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    imgUri = imageFiles[0].file.toURI().path
                }

                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    //Some error handling
                    error.printStackTrace()
                }

                override fun onCanceled(source: MediaSource) {
                    //Not necessary to remove any files manually anymore
                }
            })
    }

}