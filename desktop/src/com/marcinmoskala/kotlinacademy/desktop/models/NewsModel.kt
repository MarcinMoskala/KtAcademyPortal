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
        /** This hack is used to download images with the TornadoFX http client because this
         *  particular backend doesn't serve images to the user-agent used by JavaFX when you supply
         *  an URL to ImageView directly.
         */

        imageUrl.onChange {
            // Clear the old image before loading a new so the listview cell won't show stale images
            image.value = null

            if (it != null) {
                runAsync {
                    Image(httpClient.get(it).content())
                } ui {
                    image.value = it
                }
            }
        }
    }
}