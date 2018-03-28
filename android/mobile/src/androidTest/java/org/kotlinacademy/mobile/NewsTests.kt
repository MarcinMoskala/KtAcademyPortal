package org.kotlinacademy.mobile

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kotlinacademy.mobile.view.news.NewsActivity

@MediumTest
@RunWith(AndroidJUnit4::class)
class NewsTests {

    @Rule @JvmField var activityRule: ActivityTestRule<NewsActivity> = ActivityTestRule(NewsActivity::class.java)

    @Test
    fun loaderIsDisplayedWhenWeEnterNewsView() {
        onView(withId(R.id.progressView)).check(matches(isDisplayed()))
    }
}
