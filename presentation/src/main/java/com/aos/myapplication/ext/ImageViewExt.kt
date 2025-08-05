package com.aos.myapplication.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aos.myapplication.R
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.ic_launcher_background)
            .into(view)
    }
}