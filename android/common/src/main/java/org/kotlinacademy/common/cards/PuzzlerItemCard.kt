package org.kotlinacademy.common.cards

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import br.tiagohm.codeview.CodeView
import br.tiagohm.codeview.Language
import br.tiagohm.codeview.Theme
import org.kotlinacademy.R
import org.kotlinacademy.common.*
import org.kotlinacademy.data.*

interface PuzzlerItemCard {
    
    val titleView: TextView
    val codeQuestionView: CodeView
    val actualQuestionView: TextView
    val possibleAnswersView: TextView
    val explanationView: TextView
    val authorView: TextView
    val showAnswerButton: Button
    val shareButton: ImageView

    fun setUpPuzzlerCard(puzzler: Puzzler, baseUrl: String) {
        val context = titleView.context
        titleView.text = puzzler.title
        codeQuestionView.setTheme(Theme.AGATE)
                .setCode(puzzler.codeQuestion)
                .setLanguage(Language.KOTLIN)
                .setWrapLine(true)
                .setFontSize(14F)
                .setZoomEnabled(true)
                .apply()
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

            context.startShareIntent(puzzler.title, puzzler.getTagUrl(baseUrl))
        }
    }
}