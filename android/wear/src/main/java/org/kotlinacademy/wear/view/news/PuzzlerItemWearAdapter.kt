package org.kotlinacademy.wear.view.news

import kotlinx.android.synthetic.main.item_puzzler_wear.*
import org.kotlinacademy.wear.R
import org.kotlinacademy.common.*
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.data.*
import org.kotlinacademy.wear.App

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