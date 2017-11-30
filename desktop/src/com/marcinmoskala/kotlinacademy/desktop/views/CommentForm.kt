package com.marcinmoskala.kotlinacademy.desktop.views

import com.github.plushaze.traynotification.notification.Notifications
import com.github.plushaze.traynotification.notification.TrayNotification
import com.marcinmoskala.kotlinacademy.desktop.models.FeedbackModel
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentPresenter
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentView
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Orientation.VERTICAL
import org.controlsfx.control.Rating
import tornadofx.*

class CommentForm : View(), CommentView {
    val loadingProperty = SimpleBooleanProperty()
    override var loading by loadingProperty

    private val presenter = CommentPresenter(this)

    val feedback: FeedbackModel by inject()

    init {
        title = if (feedback.newsId.value > 0) "Comment on article" else "General comment"
    }

    override val root = form {
        fieldset(labelPosition = VERTICAL) {
            field("Rating") {
                // Rating component from ControlsFX
                // TornadoFX has an integration project for these controls, but I didn't want to add another dependency
                this += Rating().apply {
                    max = 10
                    ratingProperty().onChange {
                        feedback.rating.value = it.toInt()
                    }
                }
            }
            field("Comment") {
                textfield(feedback.comment).required()
            }
            field("Suggestions") {
                textfield(feedback.suggestions).required()
            }
        }

        button("Send") {
            enableWhen(feedback.valid)
            isDefaultButton = true
            action {
                feedback.commit {
                    presenter.onSendCommentClicked(feedback.item)
                }
            }
        }
    }

    override fun backToNewsAndShowSuccess() {
        close()

        TrayNotification().apply {
            title = "Feedback saved"
            message = "Thank you for your feedback"
            notification = Notifications.SUCCESS
            showAndDismiss(2.seconds)
        }
    }


    override fun showError(error: Throwable) = throw(error)
}