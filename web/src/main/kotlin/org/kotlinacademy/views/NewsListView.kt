package org.kotlinacademy.views

import kotlinx.html.DIV
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.kotlinacademy.common.encodeURIComponent
import org.kotlinacademy.common.routeLink
import org.kotlinacademy.common.sendEvent
import org.kotlinacademy.data.*
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.newsListView(news: List<News>): ReactElement? = div(classes = "list-center") {
    for(n in news) {
        when(n) {
            is Article -> articleCard(n)
            is Info -> infoCard(n)
            is Puzzler -> puzzlerCard(n)
        }
    }
}

private fun RDOMBuilder<DIV>.articleCard(article: Article) {
    a(classes = "article default-font", href = article.url) {
        div(classes = "article-card") {
            div(classes = "article-frame") {
                cardImage(article.imageUrl)
                h3(classes = "article-title") {
                    +article.title
                }
                div(classes = "main-text") {
                    +article.subtitle
                }
                div(classes = "news-icons-list") {
                    twitterShare("${article.title} by Kotlin Academy ${article.url.orEmpty()}")
                    facebookShare(article.url)
                    commentIcon(article)
                }
            }
        }
    }
}

private fun RDOMBuilder<DIV>.puzzlerCard(puzzler: Puzzler) {
    val buttonId = randomId()
    val answerId = randomId()

    jumpTag(name = puzzler.tag)
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
                        getById(answerId)?.show()
                        getById(buttonId)?.hide()
                        sendEvent("puzzler-show-answer", "show-answer-$buttonId", "Show answer for puzzler id $buttonId")
                    }
                }
                +"Show answer"
            }

            authorDiv(puzzler.author, puzzler.authorUrl)

            div(classes = "news-icons-list") {
                twitterShare("Puzzler \"${puzzler.title}\" on Kotlin Academy portal \n${puzzler.getTagUrl()}")
                facebookShare(puzzler.getTagUrl())
            }
        }
    }
}

private fun RDOMBuilder<DIV>.infoCard(info: Info) {
    jumpTag(name = info.tag)
    a(classes = "article default-font", href = info.url) {
        div(classes = "article-card") {
            div(classes = "article-frame") {
                cardImage(info.imageUrl)
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
            div(classes = "news-icons-list") {
                twitterShare("Puzzler \"${info.title}\" on Kotlin Academy portal \n${info.getTagUrl()}")
                facebookShare(info.getTagUrl())
            }
        }
    }
}

private fun RDOMBuilder<DIV>.jumpTag(name: String) {
    a { attrs { this.id = name } }
}

private fun RDOMBuilder<*>.cardImage(imageUrl: String) {
    div(classes = "center-text") {
        img(classes = "article-image", src = imageUrl) {}
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

private fun RDOMBuilder<*>.twitterShare(text: String) {
    val textAsPath = encodeURIComponent(text)
    a(href = "https://twitter.com/intent/tweet?text=$textAsPath") {
        img(classes = "news-icon", src = "img/twitter_icon.png") {}
    }
}

private fun RDOMBuilder<*>.facebookShare(link: String?) {
    val link = link?.takeUnless { it.isBlank() } ?: return
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