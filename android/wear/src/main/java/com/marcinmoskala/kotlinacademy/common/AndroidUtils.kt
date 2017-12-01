package com.marcinmoskala.kotlinacademy.common

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toast(text: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}