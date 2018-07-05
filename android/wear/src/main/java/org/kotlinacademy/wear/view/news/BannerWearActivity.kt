package org.kotlinacademy.wear.view.news

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_banner_wear.*
import org.kotlinacademy.wear.R

class BannerWearActivity : WearableCommentEntryActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_banner_wear)
        super.onCreate(savedInstanceState)
        listView.setOnClickListener { showNewsList() }
        commentView.setOnClickListener { showGeneralCommentScreen() }
    }

    private fun showNewsList() {
        NewsWearActivityStarter.start(this)
    }
}
