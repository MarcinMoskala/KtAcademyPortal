package com.marcinmoskala.kotlinacademy.ui.view.feedback

import activitystarter.Arg
import activitystarter.MakeActivityStarter
import android.app.Activity
import android.os.Bundle
import com.marcinmoskala.activitystarter.argExtra
import com.marcinmoskala.kotlinacademy.R
import com.marcinmoskala.kotlinacademy.data.Feedback
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentPresenter
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentView
import com.marcinmoskala.kotlinacademy.ui.view.BaseActivity
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_comment.*

@MakeActivityStarter(includeStartForResult = true)
class FeedbackActivity : BaseActivity(), CommentView {

    @get:Arg(optional = true) val newsId: Int? by argExtra()

    override var loading: Boolean by bindToVisibility(R.id.loadingView)

    private val presenter by presenter { CommentPresenter(this) }

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
        val rating = (ratingView.rating * 2).toInt()
        val commentText = commentView.text.toString()
        val suggestionsText = suggestionsView.text.toString()
        val feedback = Feedback(newsId, rating, commentText, suggestionsText)
        presenter.onSendCommentClicked(feedback)
    }
}
