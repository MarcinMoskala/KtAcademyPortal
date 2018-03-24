package org.kotlinacademy.common

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.view.View

@Suppress("unused")
open class Span {

    private var spans = emptyList<Span>()

    open fun build(builder: SpannableStringBuilder = SpannableStringBuilder()): Spannable {
        spans.forEach { span -> span.build(builder) }
        return builder
    }

    fun span(what: Any, init: Node.() -> Unit) {
        spans += Node(what).apply(init)
    }

    operator fun String.unaryPlus() {
        spans += Leaf(this)
    }

    fun bold(span: StyleSpan = StyleSpan(Typeface.BOLD), init: Span.() -> Unit) {
        span(span, init)
    }

    fun clickable(onClick: () -> Unit, init: Span.() -> Unit) {
        val span = object : ClickableSpan() {
            override fun onClick(view: View?) {
                onClick()
            }
        }
        span(span, init)
    }

    fun url(url: String, init: Span.() -> Unit) {
        span(URLSpan(url), init)
    }

    fun ln() {
        +"\n"
    }

    class Node(val span: Any) : Span() {
        override fun build(builder: SpannableStringBuilder): Spannable {
            val start = builder.length
            super.build(builder)
            builder.setSpan(span, start, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            return builder
        }
    }

    class Leaf(val content: Any) : Span() {
        override fun build(builder: SpannableStringBuilder): Spannable {
            builder.append(content.toString())
            return builder
        }
    }
}

fun span(init: Span.() -> Unit): Spannable {
    val spanWithChildren = Span()
    spanWithChildren.init()
    return spanWithChildren.build()
}