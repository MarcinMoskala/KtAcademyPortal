package org.kotlinacademy.views

import org.kotlinacademy.common.routeLink
import org.kotlinacademy.data.News
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.newsListView(newsList: List<News>?): ReactElement? = div(classes = "list-center") {
    newsList?.forEach { news ->
        a(classes = "news default-font", href = news.url) {
            div(classes = "news-card") {
                div(classes = "news-frame") {
                    div(classes = "center-text") {
                        img(classes = "news-image", src = news.imageUrl) {}
                    }
                    h3(classes = "news-title") {
                        +news.title
                    }
                    div(classes = "news-subtitle") {
                        +news.subtitle
                    }
                    div(classes = "news-icons-list") {
                        twitterShare(news)
                        facebookShare(news)
                        commentIcon(news)
                    }
                }
            }
        }
    }
}

private fun RDOMBuilder<*>.twitterShare(news: News) {
    val textAsPath = encodeURIComponent("${news.title} by Kotlin Academy ${news.url.orEmpty()}")
    a(href = "https://twitter.com/intent/tweet?text=$textAsPath") {
        img(classes = "news-icon", src = "img/twitter_icon.png") {}
    }
}

private fun RDOMBuilder<*>.facebookShare(news: News) {
    val link = news.url?.takeUnless { it.isBlank() } ?: return
    val linkAsPath = encodeURIComponent(link)
    a(href = "https://www.facebook.com/sharer/sharer.php?u=$linkAsPath%2F&amp;src=sdkpreparse") {
        setProp("target", "_blank")
        img(classes = "news-icon", src = "img/facebook_icon.png") {}
    }
}

private fun RDOMBuilder<*>.commentIcon(news: News) {
    routeLink("/feedback/${news.id}") {
        img(classes = "news-icon", src = "img/talk_icon.png") {}
    }
}

private external fun encodeURIComponent(uri: String): String