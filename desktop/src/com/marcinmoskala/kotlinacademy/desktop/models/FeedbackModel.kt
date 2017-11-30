package com.marcinmoskala.kotlinacademy.desktop.models

import com.marcinmoskala.kotlinacademy.data.Feedback
import com.marcinmoskala.kotlinacademy.data.News
import javafx.beans.property.SimpleDoubleProperty
import tornadofx.*

class FeedbackModel : ItemViewModel<Feedback>() {
    val newsId = bind(Feedback::newsId)
    val rating = bind(Feedback::rating)
    val comment = bind(Feedback::comment)
    val suggestions = bind(Feedback::suggestions)

    /**
     * Since the Feedback item isn't mutable we have to create a new instance of commit
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
        fun forItem(news: News) = FeedbackModel().apply {
            newsId.value = news.id
        }
    }
}