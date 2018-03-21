package org.kotlinacademy.views

import kotlinx.html.DIV
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.kotlinacademy.common.encodeURIComponent
import org.kotlinacademy.common.routeLink
import org.kotlinacademy.data.*
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.newsListView(news: NewsData): ReactElement? = div(classes = "list-center") {
    displayInOrder(news,
            onArticle = this::articleCard,
            onInfo = this::infoCard,
            onPuzzler = this::puzzlerCard
    )
}

// Ugly logic that should be in presenter but cannot because of Kotlin/JS problems with typing system
inline fun displayInOrder(news: NewsData, onArticle: (Article) -> Unit, onInfo: (Info) -> Unit, onPuzzler: (Puzzler) -> Unit) {
    var articles = news.articles
    var infos = news.infos
    var puzzlers = news.puzzlers
    while (articles.isNotEmpty() || infos.isNotEmpty() || puzzlers.isNotEmpty()) {
        val article: Article? = articles.maxBy { it.dateTime }
        val info: Info? = infos.maxBy { it.dateTime }
        val puzzler: Puzzler? = puzzlers.maxBy { it.dateTime }
        val first = listOfNotNull(article?.dateTime, info?.dateTime, puzzler?.dateTime).max()
                ?: break

        when (first) {
            article?.dateTime -> {
                onArticle(article)
                articles -= article
            }
            info?.dateTime -> {
                onInfo(info)
                infos -= info
            }
            puzzler?.dateTime -> {
                onPuzzler(puzzler)
                puzzlers -= puzzler
            }
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
                div(classes = "main-text") {
                    +article.subtitle
                }
                div(classes = "news-icons-list") {
                    twitterShare(article)
                    facebookShare(article)
                    commentIcon(article)
                }
            }
        }
    }
}

private fun RDOMBuilder<DIV>.puzzlerCard(puzzler: Puzzler) {
    val buttonId = randomId()
    val answerId = randomId()

    div(classes = "article-card") {
        div(classes = "article-frame") {
            h3(classes = "article-title") {
                +puzzler.title
            }
            div(classes = "main-text multiline") {
                +puzzler.question
            }
            h5(classes = "main-text bold space-top") {
                +"What does it display? Some possibilities:"
            }
            div(classes = "main-text multiline") {
                +puzzler.answers
            }

            div(classes = "answer space-top hidden") {
                attrs { id = answerId }
                h5(classes = "main-text bold") {
                    +"Correct answer"
                }
                div(classes = "main-text") {
                    +puzzler.correctAnswer
                }
                h5(classes = "main-text bold") {
                    +"Explanation"
                }
                div(classes = "main-text multiline") {
                    +puzzler.explanation
                }
            }

            button(classes = "mdc-button mdc-button--raised space-top") {
                attrs {
                    id = buttonId
                    onClickFunction = {
                        showElementWithId(answerId)
                        hideElementWithId(buttonId)
                    }
                }
                +"Show answer"
            }

            authorDiv(puzzler.author, puzzler.authorUrl)
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
                div(classes = "main-text multiline") {
                    +info.description
                }
                b(classes = "main-text bold space-top") {
                    +"Sources"
                }
                div(classes = "main-text multiline") {
                    +info.sources
                }
                authorDiv(info.author, info.authorUrl)
            }
        }
    }
}

private fun RDOMBuilder<DIV>.authorDiv(author: String?, authorUrl: String?) {
    author ?: return
    div(classes = "main-text multiline space-top") {
        +"Author: "
        if (authorUrl.isNullOrBlank()) {
            +author
        } else {
            a(href = authorUrl) { +author }
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