package academy.kot.mobile

import academy.kot.portal.android.recycler.BaseViewHolder
import academy.kot.portal.mobile.R
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasData
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class ArticleTests: NewsActivityTest() {

    @Test
    fun clickOnArticleOpensBrowserWithUrl() {
        start(loadingTime = 0)
        onView(withId(R.id.newsListView)).perform(actionOnItemAtPosition<BaseViewHolder>(1, click()))
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("https://blog.kotlin-academy.com/programmer-dictionary-receiver-type-vs-receiver-object-575d2705ddd9")))
    }
}