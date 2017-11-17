package com.marcinmoskala.kotlinacademy.ui.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadImage(photoUrl: String, resourceOnError: Int = 0) {
    Glide.with(context)
            .load(photoUrl)
            .error(resourceOnError)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
}