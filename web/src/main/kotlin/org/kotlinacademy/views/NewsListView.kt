package org.kotlinacademy.views

import org.kotlinacademy.common.routeLink
import org.kotlinacademy.data.News
import kotlinx.html.attributesMapOf
import react.RBuilder
import react.ReactElement
import react.dom.*

fun RBuilder.newsListView(newsList: List<News>?): ReactElement? = div(classes = "list-center") {
    newsList?.forEach { news ->
        a(classes = "news default-font", href = news.url) {
            div(classes = "news-card") {
                div(classes = "news-frame") {
                    img(classes = "news-image", src = news.imageUrl) {}
                    h3(classes = "news-title") { +news.title }
                    div(classes = "news-subtitle") {
                        +news.subtitle
                    }
                    div(classes = "news-icons-list") {
                        //                        div(classes = "fb-share-button") {
//                            setProp("data-href", news.url)
//                            setProp("data-layout", "button")
//                            setProp("data-size", "large")
//                            a(classes = "fb-xfbml-parse-ignore", href = "") {
//                                setProp("target", "_blank")
//                                +"Share"
//                            }
//                        }
                        val link = news.url
                        val textAsPath = encodeURIComponent("${news.title} by Kotlin Academy ${link.orEmpty()}")
                        a(href = "https://twitter.com/intent/tweet?text=$textAsPath") {
                            img(classes = "news-icon", src = "img/twitter_icon.png") {}
                        }
                        if (link != null && link.isNotBlank()) {
                            val linkAsPath = encodeURIComponent(link)
                            a(href = "https://www.facebook.com/sharer/sharer.php?u=$linkAsPath%2F&amp;src=sdkpreparse") {
                                setProp("target", "_blank")
                                img(classes = "news-icon", src = "img/facebook_icon.png") {}
                            }
                        }
                        routeLink("/feedback/${news.id}") {
                            img(classes = "news-icon", src = "img/talk_icon.png") {}
                        }
                    }
                }
            }
        }
    }
}

private external fun encodeURIComponent(uri: String): String