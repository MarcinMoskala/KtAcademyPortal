package com.marcinmoskala.kotlinacademy.desktop.models

import com.marcinmoskala.kotlinacademy.data.News
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import tornadofx.*

class NewsModel : ItemViewModel<News>() {
    val httpClient: Rest by inject()

    val id = bind(News::id)
    val title = bind(News::title)
    val subtitle = bind(News::subtitle)
    val imageUrl = bind(News::imageUrl)
    val url = bind(News::url)
    val image = SimpleObjectProperty<Image>()

    init {
        imageUrl.onChange {
            if (it != null) {
                runAsync {
                    Image(httpClient.get(it).content())
                } ui {
                    image.value = it
                }
            } else {
                image.value = null
            }
        }
    }
}