package academy.kot.portal.wear.view.news

import academy.kot.portal.wear.R
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_banner_wear.*

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
