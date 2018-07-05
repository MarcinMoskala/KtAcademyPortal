package org.kotlinacademy.desktop.views

import com.github.plushaze.traynotification.notification.Notifications
import com.github.plushaze.traynotification.notification.TrayNotification
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Orientation.VERTICAL
import org.controlsfx.control.Rating
import org.kotlinacademy.desktop.models.FeedbackModel
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.respositories.FeedbackRepositoryImpl
import tornadofx.*

class FeedbackForm : BaseTornadoView(), FeedbackView {
    private val loadingProperty = SimpleBooleanProperty()
    override var loading by loadingProperty

    private val feedbackRepository = FeedbackRepositoryImpl()
    private val presenter = FeedbackPresenter(this, feedbackRepository)

    private val feedback: FeedbackModel by inject()

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
}