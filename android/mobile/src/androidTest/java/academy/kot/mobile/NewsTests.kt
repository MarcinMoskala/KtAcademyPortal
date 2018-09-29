package academy.kot.mobile

import academy.kot.portal.mobile.R
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class NewsTests : NewsActivityTest() {

    @Test
    fun loaderIsDisplayedWhenWeEnterNewsView() = skipOnTravis {
        start(loadingTime = 10000000)
        onView(withId(R.id.progressView)).check(matches(isDisplayed()))
    }
}
