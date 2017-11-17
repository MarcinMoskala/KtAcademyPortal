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

fun EditText.setErrorId(@StringRes errorId: Int?) {
    this.error = if (errorId == null) null else context.getString(errorId)
}

fun ImageView.loadImage(photoUrl: String, resourceOnError: Int = 0) {
    Glide.with(context)
            .load(photoUrl)
            .error(resourceOnError)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
}

fun ImageView.loadImageRounded(photoUrl: String?) {
    Glide.with(context)
            .load(photoUrl)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.RESULT)
            .centerCrop()
            .into(BitmapImageCropRound(this))
}

private class BitmapImageCropRound(val img: ImageView) : BitmapImageViewTarget(img) {
    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    override fun setResource(resource: Bitmap) {
        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(img.context.resources, resource)
        circularBitmapDrawable.isCircular = true
        img.setImageDrawable(circularBitmapDrawable)
    }
}