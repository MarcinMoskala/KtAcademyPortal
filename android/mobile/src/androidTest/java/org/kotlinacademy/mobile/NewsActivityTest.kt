package org.kotlinacademy.mobile

import android.content.Intent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.kotlinacademy.DateTime
import org.kotlinacademy.common.delay
import org.kotlinacademy.common.di.NewsRepositoryDi
import org.kotlinacademy.data.*
import org.kotlinacademy.mobile.view.news.NewsActivity
import org.kotlinacademy.respositories.NewsRepository

abstract class NewsActivityTest {

    @Rule
    @JvmField
    val testRule: ActivityTestRule<NewsActivity> = IntentsTestRule(NewsActivity::class.java, true, false)

    val someArticle = Article(1, ArticleData("Programmer dictionary: Receiver type vs Receiver object", "In previous part we’ve already described difference between object and type. Similar differentiation is used for receivers in extension…", "https://cdn-images-1.medium.com/max/640/0*uGbdHQlU2qyf0VKA.jpg", "https://blog.kotlin-academy.com/programmer-dictionary-receiver-type-vs-receiver-object-575d2705ddd9", DateTime(1513233179000)))
    val someInfo = Info(1, InfoData("Kotlin is now on Android documentation", "https://scontent.fotp3-1.fna.fbcdn.net/v/t1.0-9/29177641_10213153683650531_2721993385639411712_o.jpg?oh=44b1aacd4fc006504986ee6f8949fccf&oe=5B492F1B", "For some time, examples in Android documentation are both in Kotlin and in Java, with Kotlin chosen by default. Not all examples are moved yet, but they are systematically moving them. It looks like Kotlin is becoming not only first class Android development language, but it also becomes suggested development language.", "Android documentation\n https://developer.android.com/guide/index.html", null, "Marcin Moskala", "http://marcinmoskala.com/"), DateTime(1513233179500), true)

    @Before
    fun setUp() {
        Mock.setUp()
    }

    inline fun skipOnTravis(f: ()->Unit) {
        if(!BuildConfig.BUILT_ON_TRAVIS) f()
    }

    fun start(loadingTime: Long = 0) {
        NewsRepositoryDi.mock = object : NewsRepository {
            override suspend fun getNewsData(): NewsData {
                if (loadingTime > 0) delay(loadingTime)
                val news = listOf(someArticle)
                val infos = listOf(someInfo)
                return NewsData(articles = news, infos = infos, puzzlers = emptyList())
            }
        }
        testRule.launchActivity(Intent())
    }
}