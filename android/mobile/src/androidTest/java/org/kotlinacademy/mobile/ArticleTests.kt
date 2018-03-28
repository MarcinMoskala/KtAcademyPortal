package org.kotlinacademy.mobile

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasData
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kotlinacademy.mobile.view.news.ArticleItemAdapter
import org.kotlinacademy.mobile.view.news.NewsActivity

@MediumTest
@RunWith(AndroidJUnit4::class)
class ArticleTests {

    @Rule @JvmField val testRule: ActivityTestRule<NewsActivity> = IntentsTestRule(NewsActivity::class.java)

    @Test
    fun clickOnArticleOpensBrowserWithUrl() {
        onView(withId(R.id.progressView)).check(matches(isDisplayed()))
        val progressView = testRule.activity.findViewById<View>(R.id.progressView)
        IdlingRegistry.getInstance().register(ViewVisibilityIdlingResource(progressView, View.GONE))
        onView(withId(R.id.progressView)).check(matches(not(isDisplayed())))

        onView(withId(R.id.newsListView)).perform(actionOnItemAtPosition<ArticleItemAdapter.ViewHolder>(1, click()));
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("https://blog.kotlin-academy.com/programmer-dictionary-receiver-type-vs-receiver-object-575d2705ddd9")))
    }
}
