package com.marcinmoskala.kotlinacademy.ui.view.comment

import activitystarter.Arg
import activitystarter.MakeActivityStarter
import android.app.Activity
import android.os.Bundle
import com.marcinmoskala.activitystarter.argExtra
import com.marcinmoskala.kotlinacademy.R
import com.marcinmoskala.kotlinacademy.data.Comment
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentPresenter
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentView
import com.marcinmoskala.kotlinacademy.ui.view.BaseActivity
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_comment.*

@MakeActivityStarter(includeStartForResult = true)
class CommentActivity : BaseActivity(), CommentView {

    @get:Arg(optional = true)
    val newsId: Int? by argExtra()

    override var loading: Boolean by bindToVisibility(R.id.loadingView)

    private val presenter by presenter { CommentPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        titleView.text = newsId?.run { "Comment to an article" } ?: "General comment"
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
        val comment = Comment(newsId, rating, commentText, suggestionsText)
        presenter.onSendCommentClicked(comment)
    }
}
