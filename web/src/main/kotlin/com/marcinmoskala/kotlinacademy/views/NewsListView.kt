package com.marcinmoskala.kotlinacademy.views

import com.marcinmoskala.kotlinacademy.common.routeLink
import com.marcinmoskala.kotlinacademy.data.News
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
                        routeLink("/comment/${news.id}") {
                            img(classes = "news-icon", src = "talk_icon.png") {}
                        }
                    }
                }
            }
        }
    }
}