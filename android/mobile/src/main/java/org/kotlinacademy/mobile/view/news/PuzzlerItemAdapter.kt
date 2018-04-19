package org.kotlinacademy.mobile.view.news

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.kotlinacademy.common.*
import org.kotlinacademy.common.cards.PuzzlerItemCard
import org.kotlinacademy.common.recycler.BaseViewHolder
import org.kotlinacademy.common.recycler.ItemAdapter
import org.kotlinacademy.data.*
import org.kotlinacademy.mobile.App
import org.kotlinacademy.mobile.R

class PuzzlerItemAdapter(
        private val puzzler: Puzzler
) : ItemAdapter<PuzzlerItemAdapter.ViewHolder>(R.layout.item_puzzler) {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup) = ViewHolder(itemView)

    override fun ViewHolder.onBindViewHolder() {
        setUpPuzzlerCard(puzzler, App.baseUrl.orEmpty())
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView), PuzzlerItemCard {
        override val titleView: TextView by bindView(R.id.titleView)
        override val questionView: TextView by bindView(R.id.questionView)
        override val possibleAnswersView: TextView by bindView(R.id.possibleAnswersView)
        override val explanationView: TextView by bindView(R.id.explanationView)
        override val authorView: TextView by bindView(R.id.authorView)
        override val showAnswerButton: Button by bindView(R.id.showAnswerButton)
        override val shareButton: ImageView by bindView(R.id.shareButton)
    }
}