package academy.kot.portal.android

import android.text.method.LinkMovementMethod
import android.widget.TextView

fun TextView.showAuthor(author: String?, authorUrl: String?) {
    author ?: return
    text = span {
        bold { +context.getString(R.string.author) }
        +" "
        if (authorUrl != null) url(authorUrl) { +author } else +author
    }
    movementMethod = LinkMovementMethod.getInstance()
}