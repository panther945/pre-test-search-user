package com.example.myapplication.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["requestManager", "imageUrl"], requireAll = true)
    fun loadImageUrl(imageView: ImageView, requestManager: RequestManager, imageUrl: String) {
        requestManager.load(imageUrl).into(imageView)
    }
}