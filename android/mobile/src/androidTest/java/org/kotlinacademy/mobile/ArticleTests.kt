package org.kotlinacademy.mobile

import android.content.Intent
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasData
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.kotlinacademy.mobile.view.news.ArticleItemAdapter

class ArticleTests: NewsActivityTest() {

    @Test
    fun clickOnArticleOpensBrowserWithUrl() {
        start(loadingTime = 0)
        onView(withId(R.id.newsListView)).perform(actionOnItemAtPosition<ArticleItemAdapter.ViewHolder>(1, click()));
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("https://blog.kotlin-academy.com/programmer-dictionary-receiver-type-vs-receiver-object-575d2705ddd9")))
        pressBackUnconditionally()
    }
}
