package org.kotlinacademy.mobile.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.common.*
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.data.*
import org.kotlinacademy.mobile.R
import org.kotlinacademy.respositories.BaseURL

class PuzzlerItemAdapter(
        private val puzzler: Puzzler
) : ItemAdapter<PuzzlerItemAdapter.ViewHolder>(R.layout.item_puzzler) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        val context = itemView.context
        titleView.text = puzzler.title
        questionView.text = puzzler.question
        possibleAnswersView.text = puzzler.answers
        explanationView.text = puzzler.explanation
        authorView.showAuthor(puzzler.author, puzzler.authorUrl)
        explanationView.text = span {
            bold { +context.getString(R.string.puzzler_correct_answer) }; ln()
            +puzzler.correctAnswer; ln(); ln()
            bold { +context.getString(R.string.puzzler_explanation) }; ln()
            +puzzler.explanation
        }
        setUpListeners()

    }

    private fun ViewHolder.setUpListeners() {
        val context = itemView.context
        showAnswerButton.setOnClickListener {
            showAnswerButton.hide()
            explanationView.show()
        }
        shareButton.setOnClickListener {
            context.startShareIntent(puzzler.title, puzzler.getTagUrl(BaseURL))
        }
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleView: TextView by bindView(R.id.titleView)
        val questionView: TextView by bindView(R.id.questionView)
        val possibleAnswersView: TextView by bindView(R.id.possibleAnswersView)
        val explanationView: TextView by bindView(R.id.explanationView)
        val authorView: TextView by bindView(R.id.authorView)
        val showAnswerButton: Button by bindView(R.id.showAnswerButton)
        val shareButton: ImageView by bindView(R.id.shareButton)
    }
}