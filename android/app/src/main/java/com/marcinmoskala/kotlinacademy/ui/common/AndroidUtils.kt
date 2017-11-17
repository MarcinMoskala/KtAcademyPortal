package com.marcinmoskala.kotlinacademy.ui.common

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast

fun Context.toast(text: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun TextView.onNextClicked(f: ()->Unit) {
    setOnEditorActionListener { _, _, event ->
        val goNextPressed = event != null && !event.isShiftPressed
        if (goNextPressed) f()
        goNextPressed
    }
}