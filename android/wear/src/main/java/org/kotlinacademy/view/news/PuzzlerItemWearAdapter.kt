package org.kotlinacademy.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.App
import org.kotlinacademy.R
import org.kotlinacademy.common.bindView
import org.kotlinacademy.common.cards.PuzzlerItemCard
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.data.Puzzler

class PuzzlerItemWearAdapter(
        private val puzzler: Puzzler,
        private val onLinkClicked: (String?) -> Unit
) : ItemAdapter<PuzzlerItemWearAdapter.ViewHolder>(R.layout.item_puzzler_wear) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        setUpPuzzlerCard(puzzler, App.baseUrl ?: "")
    }

    class ViewHolder(wholeView: View) : BaseViewHolder(wholeView), PuzzlerItemCard {
        override val titleView: TextView by bindView(R.id.titleView)
        override val questionView: TextView by bindView(R.id.questionView)
        override val possibleAnswersView: TextView by bindView(R.id.possibleAnswersView)
        override val explanationView: TextView by bindView(R.id.explanationView)
        override val authorView: TextView by bindView(R.id.authorView)
        override val showAnswerButton: Button by bindView(R.id.showAnswerButton)
        override val shareButton: ImageView by bindView(R.id.shareButton)
    }
}