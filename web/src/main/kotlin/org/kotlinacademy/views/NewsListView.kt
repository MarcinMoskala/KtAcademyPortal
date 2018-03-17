package org.kotlinacademy.views

import org.kotlinacademy.common.routeLink
import org.kotlinacademy.data.Article
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.newsListView(articleList: List<Article>?): ReactElement? = div(classes = "list-center") {
    articleList?.forEach { news ->
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

private fun RDOMBuilder<*>.twitterShare(article: Article) {
    val textAsPath = encodeURIComponent("${article.title} by Kotlin Academy ${article.url.orEmpty()}")
    a(href = "https://twitter.com/intent/tweet?text=$textAsPath") {
        img(classes = "news-icon", src = "img/twitter_icon.png") {}
    }
}

private fun RDOMBuilder<*>.facebookShare(article: Article) {
    val link = article.url?.takeUnless { it.isBlank() } ?: return
    val linkAsPath = encodeURIComponent(link)
    a(href = "https://www.facebook.com/sharer/sharer.php?u=$linkAsPath%2F&amp;src=sdkpreparse") {
        setProp("target", "_blank")
        img(classes = "news-icon", src = "img/facebook_icon.png") {}
    }
}

private fun RDOMBuilder<*>.commentIcon(article: Article) {
    routeLink("/feedback/${article.id}") {
        img(classes = "news-icon", src = "img/talk_icon.png") {}
    }
}

private external fun encodeURIComponent(uri: String): String