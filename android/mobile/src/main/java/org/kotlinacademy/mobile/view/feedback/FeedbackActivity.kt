package org.kotlinacademy.mobile.view.feedback

import activitystarter.Arg
import activitystarter.MakeActivityStarter
import android.app.Activity
import android.os.Bundle
import com.marcinmoskala.activitystarter.argExtra
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_comment.*
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.mobile.R
import org.kotlinacademy.mobile.view.BaseActivity
import org.kotlinacademy.presentation.comment.CommentView
import org.kotlinacademy.presentation.comment.FeedbackPresenter

@MakeActivityStarter(includeStartForResult = true)
class FeedbackActivity : BaseActivity(), CommentView {

    @get:Arg(optional = true) val newsId: Int? by argExtra()

    override var loading: Boolean by bindToVisibility(R.id.loadingView)

    private val presenter by presenter { FeedbackPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        titleView.setText(if(newsId == null) R.string.feedback_general_title else R.string.feedback_article_title)
        sendButton.setOnClickListener { sentFilledData() }
    }

    override fun backToNewsAndShowSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun sentFilledData() {
        val rating = (ratingView.rating * 2).toInt() + 1
        val commentText = commentView.text.toString()
        val suggestionsText = suggestionsView.text.toString()
        val feedback = Feedback(newsId, rating, commentText, suggestionsText)
        presenter.onSendCommentClicked(feedback)
    }
}
