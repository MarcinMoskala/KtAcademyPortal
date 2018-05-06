package org.kotlinacademy.wear.view.feedback

import activitystarter.Arg
import activitystarter.MakeActivityStarter
import android.app.Activity
import android.os.Bundle
import com.marcinmoskala.activitystarter.argExtra
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_comment_wear.*
import org.kotlinacademy.wear.R
import org.kotlinacademy.common.di.FeedbackRepositoryDi
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView
import org.kotlinacademy.wear.view.WearableBaseActivity

@MakeActivityStarter(includeStartForResult = true)
class FeedbackActivity : WearableBaseActivity(), FeedbackView {

    @get:Arg(optional = true) val newsId: Int? by argExtra()

    override var loading: Boolean by bindToVisibility(R.id.loadingView)

    private val feedbackRepository by FeedbackRepositoryDi.lazyGet()
    private val presenter by presenter { FeedbackPresenter(this, feedbackRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_wear)
        titleView.setText(if(newsId == null) R.string.feedback_general_title else R.string.feedback_article_title)
        sendButton.setOnClickListener { sentFilledData() }
    }

    override fun backToNewsAndShowSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun sentFilledData() {
        val rating = (ratingView.rating * 3).toInt() + 1
        val commentText = commentView.text.toString()
        val suggestionsText = suggestionsView.text.toString()
        val feedback = Feedback(newsId, rating, commentText, suggestionsText)
        presenter.onSendCommentClicked(feedback)
    }
}
