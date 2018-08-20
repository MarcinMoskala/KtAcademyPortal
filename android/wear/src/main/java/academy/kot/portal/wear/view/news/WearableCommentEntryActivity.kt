package academy.kot.portal.wear.view.news

import academy.kot.portal.android.toast
import academy.kot.portal.wear.R
import academy.kot.portal.wear.view.WearableBaseActivity
import academy.kot.portal.wear.view.feedback.FeedbackActivityStarter
import android.app.Activity
import android.content.Intent
import org.kotlinacademy.data.Article

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
