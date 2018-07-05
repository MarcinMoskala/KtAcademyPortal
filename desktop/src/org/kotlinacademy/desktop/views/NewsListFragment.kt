package org.kotlinacademy.desktop.views

import javafx.geometry.Pos.BOTTOM_RIGHT
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import org.kotlinacademy.data.Article
import org.kotlinacademy.desktop.Styles
import org.kotlinacademy.desktop.models.ArticleModel
import org.kotlinacademy.desktop.models.FeedbackModel
import tornadofx.*

/**
 * Represents a list cell for a news item
 */
class NewsListFragment : ListCellFragment<Article>() {
    private val news = ArticleModel().bindTo(this)

    override val root = stackpane {
        addClass(Styles.newsCard, Styles.listCenter)

        vbox {
            addClass(Styles.newsCardInner)
            imageview(news.image)

            text(news.title) {
                addClass(Styles.title)
                wrappingWidth = Styles.articleWidth
            }

            text(news.subtitle) {
                addClass(Styles.subtitle)
                wrappingWidth = Styles.articleWidth
            }

            tooltip("Double click to read article")
        }

        hbox(15) {
            stackpaneConstraints { alignment = BOTTOM_RIGHT }
            alignment = BOTTOM_RIGHT

            button {
                graphic = eyeIcon()
                addClass(Styles.icon)
                tooltip("Click to read article")
                action { hostServices.showDocument(news.url.value) }
            }

            button {
                graphic = svgicon(Styles.replyIcon)
                addClass(Styles.icon)
                tooltip("Click to leave a comment")
                action {
                    // Create a new scope and push a feedback model already tied to the news item into it
                    val editScope = Scope(FeedbackModel.forItem(item))

                    // Locate the comment form in the edit scope and show it
                    val form = find<FeedbackForm>(editScope)
                    form.openModal()
                }
            }
        }
    }

    private fun eyeIcon() = Pane().apply {
        style {
            shape = Styles.eyeIcon
            backgroundColor += Color.BLACK
            minWidth = 16.px
            minHeight = 12.px
            maxWidth = 16.px
            maxHeight = 12.px
        }
    }
}