package org.kotlinacademy.mobile.view

import android.text.method.LinkMovementMethod
import android.widget.TextView
import org.kotlinacademy.common.span

fun TextView.showAuthor(author: String?, authorUrl: String?) {
    author ?: return
    text = span {
        bold { +"Author: " }
        if (authorUrl != null) {
            url(authorUrl) { +author }
        } else {
            +author
        }
    }
    movementMethod = LinkMovementMethod.getInstance()
}