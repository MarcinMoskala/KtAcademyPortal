@file:Suppress("IllegalIdentifier")

package org.kotlinacademy

import kotlinx.coroutines.experimental.Unconfined
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.kotlinacademy.common.Cancellable
import org.kotlinacademy.common.UI
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.respositories.FeedbackRepository

class FeedbackPresenterUnitTest {

    @Before
    fun setUp() {
        UI = Unconfined
        overrideFeedbackRepository {}
    }

    @Test
    fun `When sending feedback, loader is displayed`() {
        val view = FeedbackView()
        var repositoryUsed = false
        overrideFeedbackRepository { feedback ->
            assertTrue(view.loading)
            assertEquals(FAKE_FEEDBACK, feedback)
            repositoryUsed = true
        }
        val presenter = FeedbackPresenter(view)
        assertFalse(view.loading)
        // When
        presenter.onSendCommentClicked(FAKE_FEEDBACK)
        // Then
        assertTrue(repositoryUsed)
        assertFalse(view.loading)
        view.assertNoErrors()
    }

    @Test
    fun `When repository returns error, it is shown on view`() {
        val view = FeedbackView()
        overrideFeedbackRepository { throw NORMAL_ERROR }
        val presenter = FeedbackPresenter(view)
        // When
        presenter.onSendCommentClicked(FAKE_FEEDBACK)
        // Then
        assertEquals(1, view.displayedErrors.size)
        assertEquals(NORMAL_ERROR, view.displayedErrors[0])
    }

    @Test
    fun `After data are sent, view is switching`() {
        val view = FeedbackView()
        var repositoryUsed = false
        overrideFeedbackRepository { feedback ->
            repositoryUsed = true
        }
        val presenter = FeedbackPresenter(view)
        // When
        presenter.onSendCommentClicked(FAKE_FEEDBACK)
        // Then
        assertTrue(repositoryUsed)
        assertTrue(view.viewSwitched)
        view.assertNoErrors()
    }

    private fun overrideFeedbackRepository(onAddFeedback: (Feedback) -> Unit) {
        FeedbackRepository.override = object : FeedbackRepository {
            suspend override fun addComment(feedback: Feedback) {
                onAddFeedback(feedback)
            }
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

    companion object {
        val FAKE_FEEDBACK = Feedback(1, 7, "Some comment", "Some suggestions")
        val NORMAL_ERROR = Error()
    }
}