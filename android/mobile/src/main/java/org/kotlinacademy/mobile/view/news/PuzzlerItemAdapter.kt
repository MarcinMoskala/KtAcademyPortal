package org.kotlinacademy.mobile.view.news

import br.tiagohm.codeview.Language
import br.tiagohm.codeview.Theme
import kotlinx.android.synthetic.main.item_puzzler.*
import org.kotlinacademy.common.*
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.data.*
import org.kotlinacademy.mobile.App
import org.kotlinacademy.mobile.R

class PuzzlerItemAdapter(
        private val puzzler: Puzzler
) : ItemAdapter(R.layout.item_puzzler) {

    override fun BaseViewHolder.onBindViewHolder() {
        titleView.text = puzzler.title
        codeQuestionView.setTheme(Theme.AGATE)
                .setCode(puzzler.codeQuestion + "\n") // Hack: Last line is not displayed
                .setLanguage(Language.KOTLIN)
                .setWrapLine(false)
                .setFontSize(14F)
                .setZoomEnabled(true)
                .apply()
        actualQuestionView.text = puzzler.actualQuestion
        possibleAnswersView.text = puzzler.answers
        explanationView.text = puzzler.explanation
        authorView.showAuthor(puzzler.author, puzzler.authorUrl)
        explanationView.text = span {
            bold { +context.getString(org.kotlinacademy.R.string.puzzler_correct_answer) }; ln()
            +puzzler.correctAnswer; ln(); ln()
            bold { +context.getString(org.kotlinacademy.R.string.puzzler_explanation) }; ln()
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