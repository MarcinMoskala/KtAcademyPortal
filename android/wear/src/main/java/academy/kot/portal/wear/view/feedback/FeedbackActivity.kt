package academy.kot.portal.wear.view.feedback

import academy.kot.portal.android.di.FeedbackRepositoryDi
import academy.kot.portal.wear.R
import academy.kot.portal.wear.view.WearableBaseActivity
import activitystarter.Arg
import activitystarter.MakeActivityStarter
import android.app.Activity
import android.os.Bundle
import com.marcinmoskala.activitystarter.argExtra
import com.marcinmoskala.kotlinandroidviewbindings.bindToVisibility
import kotlinx.android.synthetic.main.activity_comment_wear.*
import kotlinx.coroutines.experimental.android.UI
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.presentation.feedback.FeedbackPresenter
import org.kotlinacademy.presentation.feedback.FeedbackView

@MakeActivityStarter(includeStartForResult = true)
class FeedbackActivity : WearableBaseActivity(), FeedbackView {

    @get:Arg(optional = true)
    val newsId: Int? by argExtra()

    override var loading: Boolean by bindToVisibility(R.id.loadingView)

    private val feedbackRepository by FeedbackRepositoryDi.lazyGet()
    private val presenter by presenter { FeedbackPresenter(UI, this, feedbackRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_wear)
        setUpTitle()
        sendButton.setOnClickListener { sentFilledData() }
    }

    override fun backToNewsAndShowSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun setUpTitle() {
        val title =
                if (newsId == null) R.string.feedback_general_title
                else R.string.feedback_article_title
        titleView.setText(title)
    }

    private fun sentFilledData() {
        val rating = (ratingView.rating * 3).toInt() + 1
        val commentText = commentView.text.toString()
        val suggestionsText = suggestionsView.text.toString()
        val feedback = Feedback(newsId, rating, commentText, suggestionsText)
        presenter.onSendCommentClicked(feedback)
    }
}
