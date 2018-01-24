package org.kotlinacademy

import org.kotlinacademy.data.Feedback
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.respositories.FeedbackRepository
import kotlin.test.*

class FeedbackPresenterUnitTest {

    @BeforeTest
    fun setUp() {
        overrideFeedbackRepository {}
    }

    @JsName("dataSendingTest")
    @Test
    fun `Sends all data provided in form`() {
        var sentFeedback: Feedback? = null
        val view = FeedbackView()
        overrideFeedbackRepository { feedback ->
            sentFeedback = feedback
        }
        val presenter = FeedbackPresenter(view)
        // When
        presenter.onSendCommentClicked(FAKE_FEEDBACK)
        // Then
        assertEquals(FAKE_FEEDBACK, sentFeedback)
        view.assertNoErrors()
    }

    @JsName("loaderTest")
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

    @JsName("sendingErrorTest")
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

    @JsName("moveNextTest")
    @Test
    fun `After data are sent, view is switching back to news list`() {
        val view = FeedbackView()
        var repositoryUsed = false
        overrideFeedbackRepository { _ ->
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
            suspend override fun addFeedback(feedback: Feedback) {
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
        val NORMAL_ERROR = Throwable()
    }
}