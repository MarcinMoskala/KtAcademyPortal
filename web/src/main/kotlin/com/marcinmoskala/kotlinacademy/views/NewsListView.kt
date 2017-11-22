package com.marcinmoskala.kotlinacademy.views

import com.marcinmoskala.kotlinacademy.data.News
import react.RBuilder
import react.ReactElement
import react.dom.a
import react.dom.div
import react.dom.h3
import react.dom.img

fun RBuilder.newsListView(newsList: List<News>?): ReactElement? = div(classes = "list-center") {
    newsList?.forEach { news ->
        a(classes = "news default-font", href = news.url) {
            div(classes = "news-card") {
                div(classes = "news-frame") {
                    img(classes = "news-image", src = news.imageUrl) {  }
                    h3(classes = "news-title") { +news.title }
                    div(classes = "news-subtitle") {
                        +news.subtitle
                    }
                }
            }
        }
    }
}
