package org.kotlinacademy.desktop.models

import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Feedback
import tornadofx.ItemViewModel

class FeedbackModel : ItemViewModel<Feedback>() {
    val newsId = bind(Feedback::newsId)
    val rating = bind(Feedback::rating)
    val comment = bind(Feedback::comment)
    val suggestions = bind(Feedback::suggestions)

    /**
     * Since the Feedback item isn't mutable we have to create a new instance on commit
     */
    override fun onCommit() {
        item = Feedback(
                newsId.value,
                rating.value,
                comment.value,
                suggestions.value
        )
    }

    companion object {
        fun forItem(news: Article) = FeedbackModel().apply {
            newsId.value = news.id
        }
    }
}