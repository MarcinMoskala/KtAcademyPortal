package com.marcinmoskala.kotlinacademy.ui.view.comment

import activitystarter.Arg
import android.os.Bundle
import com.marcinmoskala.activitystarter.argExtra
import com.marcinmoskala.kotlinacademy.R
import com.marcinmoskala.kotlinacademy.data.Comment
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentPresenter
import com.marcinmoskala.kotlinacademy.presentation.comment.CommentView
import com.marcinmoskala.kotlinacademy.ui.data.NewsModel
import com.marcinmoskala.kotlinacademy.ui.view.BaseActivity
import com.marcinmoskala.kotlinandroidviewbindings.bindToLoading
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : BaseActivity(), CommentView {

    @get:Arg(optional = true)
    val newsModel: NewsModel? by argExtra()

    override var loading: Boolean by bindToVisibility(R.id.loadingView)

    private val presenter by presenter { CommentPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        titleView.text = newsModel?.run { "Comment to $title" } ?: "General comment"
        sendButton.setOnClickListener {
            val rating = (ratingView.rating * 2).toInt()
            val commentText = commentView.text.toString()
            val suggestionsText = suggestionsView.text.toString()
            val comment = Comment(newsModel?.id, rating, commentText, suggestionsText)
            presenter.onSendComment(comment)
        }
    }

    override fun backToNewsList() {
        finish()
    }
}
