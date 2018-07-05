package org.kotlinacademy.wear.view.news

import android.app.Activity
import android.content.Intent
import org.kotlinacademy.R
import org.kotlinacademy.common.toast
import org.kotlinacademy.data.Article
import org.kotlinacademy.wear.view.WearableBaseActivity
import org.kotlinacademy.wear.view.feedback.FeedbackActivityStarter

abstract class WearableCommentEntryActivity : WearableBaseActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            requestCode == COMMENT_CODE && resultCode == Activity.RESULT_OK -> showThankYouForCommentSnack()
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    protected fun showNewsCommentScreen(article: Article) {
        FeedbackActivityStarter.startForResult(this, article.id, COMMENT_CODE)
    }

    protected fun showGeneralCommentScreen() {
        FeedbackActivityStarter.startForResult(this, COMMENT_CODE)
    }

    private fun showThankYouForCommentSnack() {
        toast(getString(R.string.feedback_response))
    }

    companion object {
        val COMMENT_CODE = 144
    }
}
