package com.afonsofrancof.connectdot.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.afonsofrancof.connectdot.databinding.FragmentPostCreateBinding
import com.bumptech.glide.Glide
import pl.aprilapps.easyphotopicker.EasyImage
import java.net.URI
import pl.aprilapps.easyphotopicker.MediaSource

import androidx.annotation.NonNull

import pl.aprilapps.easyphotopicker.MediaFile

import pl.aprilapps.easyphotopicker.DefaultCallback

import android.content.Intent




class PostCreateFragment : Fragment() {

    lateinit var binding: FragmentPostCreateBinding

    lateinit var easyImage : EasyImage

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostCreateBinding.inflate(inflater)
        easyImage = EasyImage.Builder(requireContext())
            .allowMultiple(false)
            .build()
        binding.addImageButton.setOnClickListener {
            easyImage.openCameraForImage(this)

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