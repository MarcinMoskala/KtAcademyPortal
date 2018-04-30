package org.kotlinacademy

import org.kotlinacademy.data.Feedback
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.respositories.FeedbackRepository
import kotlin.test.*

class FeedbackPresenterUnitTest : BaseUnitTest() {

    @JsName("dataSendingTest")
    @Test
    fun `Sends all data provided in form`() {
        var sentFeedback: Feedback? = null
        val view = FeedbackView()
        val repo = feedbackRepository { feedback ->
            sentFeedback = feedback
        }
        val presenter = FeedbackPresenter(view, repo)
        // When
        presenter.onSendCommentClicked(someFeedback)
        // Then
        assertEquals(someFeedback, sentFeedback)
        view.assertNoErrors()
    }

    @JsName("loaderTest")
    @Test
    fun `When sending feedback, loader is displayed`() {
        val view = FeedbackView()
        var repositoryUsed = false
        val repo = feedbackRepository { feedback ->
            assertTrue(view.loading)
            assertEquals(someFeedback, feedback)
            repositoryUsed = true
        }
        val presenter = FeedbackPresenter(view, repo)
        assertFalse(view.loading)
        // When
        presenter.onSendCommentClicked(someFeedback)
        // Then
        assertTrue(repositoryUsed)
        assertFalse(view.loading)
        view.assertNoErrors()
    }

    @JsName("sendingErrorTest")
    @Test
    fun `When repository returns error, it is shown on view`() {
        val view = FeedbackView()
        val repo = feedbackRepository { throw someError }
        val presenter = FeedbackPresenter(view, repo)
        // When
        presenter.onSendCommentClicked(someFeedback)
        // Then
        assertEquals(1, view.displayedErrors.size)
        assertEquals(someError, view.displayedErrors[0])
    }

    @JsName("moveNextTest")
    @Test
    fun `After data are sent, view is switching back to news list`() {
        val view = FeedbackView()
        var repositoryUsed = false
        val repo = feedbackRepository { _ ->
            repositoryUsed = true
        }
        val presenter = FeedbackPresenter(view, repo)
        // When
        presenter.onSendCommentClicked(someFeedback)
        // Then
        assertTrue(repositoryUsed)
        assertTrue(view.viewSwitched)
        view.assertNoErrors()
    }

    private fun feedbackRepository(onAddFeedback: (Feedback) -> Unit) = object : FeedbackRepository {
        override suspend fun addFeedback(feedback: Feedback) {
            onAddFeedback(feedback)
        }
    }

    private fun FeedbackView() = object : FeedbackView {
        override var loading: Boolean = false
        var displayedErrors: List<Throwable> = emptyList()
        var viewSwitched = false

        override fun backToNewsAndShowSuccess() {
            viewSwitched = true
        }

        override fun showError(error: Throwable) {
            displayedErrors += error
        }

        override fun logError(error: Throwable) {
            throw error
        }

        fun assertNoErrors() {
            displayedErrors.forEach { throw it }
            assertEquals(0, displayedErrors.size)
        }
    }
}