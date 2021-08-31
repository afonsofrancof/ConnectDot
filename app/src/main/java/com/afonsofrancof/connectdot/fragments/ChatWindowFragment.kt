package com.afonsofrancof.connectdot.fragments

import android.app.Activity
import android.content.Intent
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
import com.afonsofrancof.connectdot.DividerItemDecoration
import com.afonsofrancof.connectdot.R
import com.afonsofrancof.connectdot.databinding.FragmentChatWindowBinding
import com.afonsofrancof.connectdot.objects.Chat
import com.afonsofrancof.connectdot.utils.getUser
import com.afonsofrancof.connectdot.viewModels.ChatWindowViewModel
import com.bumptech.glide.Glide
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource

class ChatWindowFragment : Fragment(), ChatWindowAdapter.OnClickListener {

    private val viewModel: ChatWindowViewModel by lazy {
        ViewModelProvider(this).get(ChatWindowViewModel::class.java)
    }

    val args: ChatWindowFragmentArgs by navArgs()

    lateinit var easyImage : EasyImage

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
            binding.previewImage.visibility = View.GONE
            binding.deletePreviewImage.visibility = View.GONE
            viewModel.createMessage(binding.textInput.text.toString())
            viewModel.imgUri = null
        }
        binding.deletePreviewImage.setOnClickListener {
            binding.previewImage.visibility = View.GONE
            binding.deletePreviewImage.visibility = View.GONE
            viewModel.imgUri = null
        }
        viewModel.chat.observe(viewLifecycleOwner, Observer {
            val messages = it.messages.sortedBy { msg -> msg.timeStamp.time }.reversed()
            adapter.submitList(messages)
            if ((binding.messageRecyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition() < 3) {
                binding.messageRecyclerView.smoothScrollToPosition(0)
            }
            if(messages.isNotEmpty())
            if (messages.first().userId == getUser().userId) {
                binding.textInput.text = null
                binding.messageRecyclerView.smoothScrollToPosition(0)
            }
        })
        binding.messageRecyclerView.addOnScrollListener(ScrollListener(binding.scrollToBottom))
        binding.scrollToBottom.setOnClickListener {
            binding.messageRecyclerView.smoothScrollToPosition(
                0
            )
        }
        easyImage = EasyImage.Builder(requireContext())
            .allowMultiple(false)
            .build()
        binding.addImageButton.setOnClickListener {
            easyImage.openCameraForImage(this)
        }
        return binding.root
    }

    class ScrollListener(val button : View) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if ((recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition() > 0) {
                button.visibility = View.VISIBLE
            } else {
                button.visibility = View.GONE
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            activity as Activity,
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                   viewModel.imgUri = imageFiles.first().file.path.toString()
                    binding.previewImage.visibility = View.VISIBLE
                    binding.deletePreviewImage.visibility = View.VISIBLE
                    Glide.with(requireContext()).load(viewModel.imgUri).placeholder(R.drawable.ic__image_placeholder).error(R.drawable.ic_error_image).into(binding.previewImage)

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

