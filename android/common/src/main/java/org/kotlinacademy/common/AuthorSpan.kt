package org.kotlinacademy.common

import android.text.method.LinkMovementMethod
import android.widget.TextView
import org.kotlinacademy.R

fun TextView.showAuthor(author: String?, authorUrl: String?) {
    author ?: return
    text = span {
        bold { +context.getString(R.string.author) }
        +" "
        if (authorUrl != null) url(authorUrl) { +author } else +author
    }
    movementMethod = LinkMovementMethod.getInstance()
}