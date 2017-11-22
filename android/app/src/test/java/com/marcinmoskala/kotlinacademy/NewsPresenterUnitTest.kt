@file:Suppress("IllegalIdentifier")

package com.marcinmoskala.kotlinacademy

import com.marcinmoskala.kotlinacademy.common.Cancellable
import com.marcinmoskala.kotlinacademy.common.UI
import com.marcinmoskala.kotlinacademy.common.launchUI
import com.marcinmoskala.kotlinacademy.data.News
import com.marcinmoskala.kotlinacademy.data.NewsData
import com.marcinmoskala.kotlinacademy.presentation.news.NewsPresenter
import com.marcinmoskala.kotlinacademy.presentation.news.NewsView
import com.marcinmoskala.kotlinacademy.respositories.NewsRepository
import com.marcinmoskala.kotlinacademy.usecases.PeriodicCaller
import kotlinx.coroutines.experimental.CommonPool
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsPresenterUnitTest {

    @Before
    fun setUp() {
        UI = CommonPool
        overrideNewsRepository({ NewsData(emptyList()) })
        overridePeriodicCaller({ _, _ -> object : Cancellable {} })
    }

    @Test
    fun `Presenter is using PeriodicCaller to refresh list since onCreate`() {
        val view = NewsView()
        var periodicCallerStarts: List<Long> = listOf()
        overridePeriodicCaller { timeMillis, callback ->
            periodicCallerStarts += timeMillis
            Cancellable()
        }
        val presenter = NewsPresenter(view)
        // When
        presenter.onCreate()
        // Then
        assertEquals(1, periodicCallerStarts.size)
        assertEquals(NewsPresenter.AUTO_REFRESH_TIME_MS, periodicCallerStarts[0])
    }

    fun overrideNewsRepository(getNewsData: () -> NewsData) {
        NewsRepository.override = object : NewsRepository {
            suspend override fun getNewsData(): NewsData = getNewsData()
        }
    }

    fun overridePeriodicCaller(start: (timeMillis: Long, callback: () -> Unit) -> Cancellable) {
        PeriodicCaller.override = object : PeriodicCaller {
            override fun start(timeMillis: Long, callback: () -> Unit) = start(timeMillis, callback)
        }
    }

    fun NewsView(
            onShowList: (news: List<News>) -> Unit = {},
            onShowError: (Throwable) -> Unit = {}
    ) = object : NewsView {
        override var loading: Boolean = false
        override var swipeRefresh: Boolean = false

        override fun showList(news: List<News>) {
            onShowList(news)
        }

        override fun showError(throwable: Throwable) {
            onShowError(throwable)
        }
    }

    fun Cancellable() = object : Cancellable {}
}
