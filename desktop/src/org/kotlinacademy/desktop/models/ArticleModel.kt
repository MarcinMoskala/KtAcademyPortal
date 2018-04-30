package org.kotlinacademy.desktop.models

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.kotlinacademy.data.*
import tornadofx.ItemViewModel
import tornadofx.Rest
import tornadofx.onChange

class ArticleModel : ItemViewModel<Article>() {
    private val httpClient: Rest by inject()

    val title = bind(Article::title)
    val subtitle = bind(Article::subtitle)
    val imageUrl = bind(Article::imageUrl)
    val url = bind(Article::url)
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