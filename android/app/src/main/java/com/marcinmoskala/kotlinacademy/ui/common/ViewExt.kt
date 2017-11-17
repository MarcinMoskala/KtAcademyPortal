package com.marcinmoskala.kotlinacademy.ui.common

import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget

fun ImageView.loadImage(photoUrl: String, resourceOnError: Int = 0) {
    Glide.with(context)
            .load(photoUrl)
            .error(resourceOnError)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
}