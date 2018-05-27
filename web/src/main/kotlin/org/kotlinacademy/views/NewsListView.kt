package org.kotlinacademy.views

import kotlinx.html.DIV
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.kotlinacademy.DateTime
import org.kotlinacademy.Endpoints
import org.kotlinacademy.common.*
import org.kotlinacademy.data.*
import org.kotlinacademy.kebabCase
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.newsListView(news: List<News>): ReactElement? = div(classes = "list-center") {
    for (n in news) {
        when (n) {
            is Article -> articleCard(n)
            is Info -> infoCard(n)
            is Puzzler -> puzzlerCard(n)
        }
    }
}

fun RDOMBuilder<DIV>.articleCard(article: Article) {
    aAction(classes = "article default-font", href = article.url, category = "article", extra = article.title.kebabCase()) {
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
                    secretInUrl?.let { secret ->
                        a(target = "_blank", href = "article/${article.id}/delete?Secret-hash=$secret") {
                            img(classes = "news-icon", src = "img/delete_icon.png") {}
                        }
                    }
                }
            }
        }
    }
}

fun RDOMBuilder<DIV>.puzzlerCard(puzzler: Puzzler) {
    val buttonId = randomId()
    val answerId = randomId()
    val codeBlockId = randomId()

    jumpTag(name = puzzler.tag)
    div(classes = "article-card") {
        div(classes = "article-frame") {
            h3(classes = "article-title") {
                +puzzler.title
            }
            puzzler.level?.let { level ->
                div {
                    b { +"Level: " }
                    +level
                }
            }
            pre {
                code(classes = "kotlin initially-hidden-start-button") {
                    attrs { id = codeBlockId }
                    +puzzler.codeQuestion
                }
            }
            h5(classes = "main-text bold space-top") {
                +puzzler.actualQuestion
            }
            div(classes = "main-text multiline") {
                +puzzler.answers
            }

            div(classes = "answer space-top space-bottom hidden") {
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

            materialButton {
                attrs {
                    id = buttonId
                    onClick = {
                        getById(answerId)?.show()
                        getById(buttonId)?.hide()
                        getById(codeBlockId)?.getStartButtonForCodeBlock()?.show()
                        sendEvent("puzzler", "show-answer-$buttonId", "Show answer for puzzler id $buttonId")
                    }
                }
                +"Show answer"
            }

            authorDiv(puzzler.author, puzzler.authorUrl)

            div(classes = "news-icons-list") {
                twitterShare("Puzzler \"${puzzler.title}\" on Kotlin Academy portal \n${puzzler.getTagUrl()}")
                facebookShare(puzzler.getTagUrl())
                secretInUrl?.let { secret ->
                    a(href = "#/submit-puzzler?id=${puzzler.id}&${Endpoints.webSecretParam}=$secret") {
                        img(classes = "news-icon", src = "img/edit.png") {}
                    }
                    a(target = "_blank", href = "${Endpoints.puzzler}/${puzzler.id}/${Endpoints.unpublish}?${Endpoints.apiSecretKey}=$secret") {
                        img(classes = "news-icon", src = "img/delete_icon.png") {}
                    }
                }
            }
        }
    }
}

fun RDOMBuilder<DIV>.infoCard(info: Info) {
    jumpTag(name = info.tag)
    a(classes = "article default-font", href = info.url, target = "_blank") {
        div(classes = "article-card") {
            div(classes = "article-frame") {
                cardImage(info.imageUrl)
                if (info.title.isNotBlank()) {
                    h3(classes = "article-title") {
                        +info.title
                    }
                }
                if (info.description.isNotBlank()) {
                    div(classes = "main-text multiline") {
                        +info.description
                    }
                }
                if (info.sources.isNotBlank()) {
                    b(classes = "main-text bold space-top") {
                        +"Sources"
                    }
                    div(classes = "main-text multiline") {
                        +info.sources
                    }
                }
                authorDiv(info.author, info.authorUrl)
                dateField(info.dateTime)
            }
            div(classes = "news-icons-list") {
                twitterShare("News \"${info.title}\" on Kotlin Academy portal \n${info.getTagUrl()}")
                facebookShare(info.getTagUrl())
                secretInUrl?.let { secret ->
                    a(href = "#/submit-info?id=${info.id}&secret=$secret") {
                        img(classes = "news-icon", src = "img/edit.png") {}
                    }
                }
            }
        }
    }
}

private fun RDOMBuilder<DIV>.jumpTag(name: String) {
    a { attrs { this.id = name } }
}

private fun RDOMBuilder<DIV>.dateField(date: DateTime) {
    div(classes = "main-text") {
        +date.run { "Date: $dayOfMonth.$monthOfYear.$year" }
    }
}

private fun RDOMBuilder<*>.cardImage(imageUrl: String) {
    div(classes = "center-text") {
        img(classes = "article-image", src = imageUrl) {}
    }
}

private fun RDOMBuilder<DIV>.authorDiv(author: String?, authorUrl: String?) {
    author ?: return
    div(classes = "main-text space-top") {
        +"Author: "
        if (authorUrl.isNullOrBlank()) {
            +author
        } else {
            aAction(href = authorUrl, category = "author", action = "open", extra = author.kebabCase()) { +author }
        }
    }
}

private fun RDOMBuilder<*>.twitterShare(text: String) {
    val textAsPath = encodeURIComponent(text)
    aAction(href = "https://twitter.com/intent/tweet?text=$textAsPath", category = "twitter", action = "share") {
        img(classes = "news-icon", src = "img/twitter_icon.png") {}
    }
}

private fun RDOMBuilder<*>.facebookShare(link: String?) {
    val link = link?.takeUnless { it.isBlank() } ?: return
    val linkAsPath = encodeURIComponent(link)
    aAction(href = "https://www.facebook.com/sharer/sharer.php?u=$linkAsPath%2F&amp;src=sdkpreparse", category = "facebook", action = "share", extra = link) {
        img(classes = "news-icon", src = "img/facebook_icon.png") {}
    }
}

private fun RDOMBuilder<*>.commentIcon(article: Article) {
    routeLink("/feedback/${article.id}") {
        img(classes = "news-icon", src = "img/talk_icon.png") {}
    }
}