package org.kotlinacademy.desktop.models

import org.kotlinacademy.data.News
import tornadofx.*

class NewsModel : ItemViewModel<News>() {
    private val httpClient: Rest by inject()

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