package academy.kot.portal.wear.view.news

import academy.kot.portal.android.*
import academy.kot.portal.android.recycler.BaseViewHolder
import academy.kot.portal.android.recycler.ItemAdapter
import academy.kot.portal.wear.App
import academy.kot.portal.wear.R
import kotlinx.android.synthetic.main.item_puzzler_wear.*
import org.kotlinacademy.data.*

class PuzzlerItemWearAdapter(
        private val puzzler: Puzzler
) : ItemAdapter(R.layout.item_puzzler_wear) {

    override fun BaseViewHolder.onBindViewHolder() {
        titleView.text = puzzler.title
        codeQuestionView.text = puzzler.codeQuestion
        actualQuestionView.text = puzzler.actualQuestion
        possibleAnswersView.text = puzzler.answers
        explanationView.text = puzzler.explanation
        authorView.showAuthor(puzzler.author, puzzler.authorUrl)
        explanationView.text = span {
            bold { +context.getString(R.string.puzzler_correct_answer) }; ln()
            +puzzler.correctAnswer; ln(); ln()
            bold { +context.getString(R.string.puzzler_explanation) }; ln()
            +puzzler.explanation
        }
        showAnswerButton.setOnClickListener {
            showAnswerButton.hide()
            explanationView.show()
        }
        shareButton.setOnClickListener {
            context.startShareIntent(puzzler.title, puzzler.getTagUrl(App.baseUrl ?: ""))
        }
    }
}