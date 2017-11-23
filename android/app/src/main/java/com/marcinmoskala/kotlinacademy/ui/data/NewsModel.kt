package com.marcinmoskala.kotlinacademy.ui.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.marcinmoskala.kotlinacademy.data.News
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class NewsModel(
    val id: Int,
    val title: String
): Parcelable {
    constructor(news: News): this(news.id!!, news.title)
}