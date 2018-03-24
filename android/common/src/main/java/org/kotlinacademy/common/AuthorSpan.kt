package org.kotlinacademy.common

import android.text.method.LinkMovementMethod
import android.widget.TextView
import org.kotlinacademy.R

fun TextView.showAuthor(author: String?, authorUrl: String?, onClick: ((String?) -> Unit)? = null) {
    author ?: return
    text = span {
        bold { +context.getString(R.string.author) }
        +" "
        when {
            onClick != null -> clickable(onClick = { onClick(authorUrl) }) { +author }
            authorUrl != null -> url(authorUrl) { +author }
            else -> +author
        }
    }
    movementMethod = LinkMovementMethod.getInstance()
}