package org.kotlinacademy.views

import kotlinx.html.DIV
import org.kotlinacademy.common.routeLink
import org.kotlinacademy.components.Newses
import org.kotlinacademy.data.*
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.newsListView(newses: Newses): ReactElement? = div(classes = "list-center") {
    for (info in newses.infos) {
        infoCard(info)
    }

    for (puzzler in newses.puzzlers) {
        puzzlerCard(puzzler)
    }

    for (article in newses.articles) {
        articleCard(article)
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
    div(classes = "article-card") {
        div(classes = "article-frame") {
            h3(classes = "article-title") {
                +puzzler.title
            }
            div(classes = "article-subtitle") {
                +puzzler.question
            }
        }
    }
}

private fun RDOMBuilder<DIV>.infoCard(info: Info) {
    a(classes = "article default-font", href = info.url) {
        div(classes = "article-card") {
            div(classes = "article-frame") {
                div(classes = "center-text") {
                    img(classes = "article-image", src = info.imageUrl) {}
                }
                h3(classes = "article-title") {
                    +info.title
                }
                div(classes = "article-subtitle") {
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