package com.afonsofrancof.connectdot

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.afonsofrancof.connectdot.databinding.ActivityZoomableImageBinding
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class ZoomableImageActivity : AppCompatActivity() {

    lateinit var binding: ActivityZoomableImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityZoomableImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra("url")
//
        Glide.with(this).load(url).placeholder(R.drawable.ic__image_placeholder)
            .error(R.drawable.ic_error_image).into(binding.zoomableImage)
        binding.closeImageButton.setOnClickListener {
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val zoomHelper = ZoomHelper.getInstance()
        zoomHelper.minScale = 1f
        zoomHelper.maxScale = 10f
        zoomHelper.dismissDuration = 200
        return zoomHelper.dispatchTouchEvent(ev!!, this) || super.dispatchTouchEvent(ev)
    }


}