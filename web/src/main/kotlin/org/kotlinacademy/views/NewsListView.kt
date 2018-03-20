package org.kotlinacademy.views

import kotlinx.html.DIV
import org.kotlinacademy.common.routeLink
import org.kotlinacademy.data.*
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.newsListView(articleList: List<News>?): ReactElement? = div(classes = "list-center") {
    for (news in articleList.orEmpty()) {
        when (news) {
            is Article -> articleCard(news)
            is Puzzler -> puzzlerCard(news)
            is Info -> infoCard(news)
        }
    }
}

private fun RDOMBuilder<DIV>.articleCard(article: Article) {
    a(classes = "article default-font", href = article.url) {
        div(classes = "article-card") {
            div(classes = "article-frame") {
                div(classes = "center-text") {
                    img(classes = "article-image", src = article.imageUrl) {}
                }
                h3(classes = "article-title") {
                    +article.title
                }
                div(classes = "article-subtitle") {
                    +article.subtitle
                }
                div(classes = "article-icons-list") {
                    twitterShare(article)
                    facebookShare(article)
                    commentIcon(article)
                }
            }
        }
    }
}

private fun RDOMBuilder<DIV>.puzzlerCard(puzzler: Puzzler) {
    div(classes = "puzzler-card") {
        div(classes = "puzzler-frame") {
            h3(classes = "puzzler-title") {
                +puzzler.title
            }
            div(classes = "puzzler-subtitle") {
                +puzzler.question
            }
        }
    }
}

private fun RDOMBuilder<DIV>.infoCard(info: Info) {
    a(classes = "puzzler default-font", href = info.url) {
        div(classes = "puzzler-card") {
            div(classes = "puzzler-frame") {
                div(classes = "center-text") {
                    img(classes = "puzzler-image", src = info.imageUrl) {}
                }
                h3(classes = "puzzler-title") {
                    +info.title
                }
                div(classes = "puzzler-subtitle") {
                    +info.description
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