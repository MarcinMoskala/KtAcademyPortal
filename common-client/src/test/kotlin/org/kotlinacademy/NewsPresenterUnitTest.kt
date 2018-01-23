package org.kotlinacademy

import org.kotlinacademy.common.Cancellable
import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.respositories.NewsRepository
import org.kotlinacademy.usecases.PeriodicCaller
import kotlin.test.*

class NewsPresenterUnitTest {

    @BeforeTest
    fun setUp() {
        overrideNewsRepository({ NewsData(emptyList()) })
        overridePeriodicCaller({ _, _ -> object : Cancellable {} })
    }

    // When onCreate, loads and displays list of news
    @Test
    fun gettingAndDisplayingTest() {
        val view = NewsView()
        overrideNewsRepository { NewsData(FAKE_NEWS_LIST_1) }
        val presenter = NewsPresenter(view)
        // When
        presenter.onCreate()
        // Then
        assertEquals(FAKE_NEWS_LIST_1, view.newsList)
        view.assertNoErrors()
    }

    // When onCreate, loader is displayed during repository usage but not before and after onCreate operation
    @Test
    fun loaderTest() {
        val view = NewsView()

        var repositoryUsed = false
        overrideNewsRepository {
            assertTrue(view.loading)
            repositoryUsed = true
            NewsData(FAKE_NEWS_LIST_1)
        }
        val presenter = NewsPresenter(view)
        assertFalse(view.loading)
        // When
        presenter.onCreate()
        // Then
        assertTrue(repositoryUsed)
        assertFalse(view.loading)
        assertEquals(FAKE_NEWS_LIST_1, view.newsList)
        view.assertNoErrors()
    }

    // When repository returns error, it is shown on view
    @Test
    fun errorTest() {
        val view = NewsView()
        overrideNewsRepository { throw NORMAL_ERROR }
        val presenter = NewsPresenter(view)
        // When
        presenter.onCreate()
        // Then
        assertNull(view.newsList)
        assertEquals(1, view.displayedErrors.size)
        assertEquals(NORMAL_ERROR, view.displayedErrors[0])
    }

    // Is using PeriodicCaller to refresh list since onCreate
    @Test
    fun periodicRefreshTest() {
        val view = NewsView()
        var periodicCallerStarts: List<Long> = listOf()
        overridePeriodicCaller { timeMillis, _ ->
            periodicCallerStarts += timeMillis
            Cancellable()
        }
        val presenter = NewsPresenter(view)
        // When
        presenter.onCreate()
        // Then
        assertEquals(1, periodicCallerStarts.size)
        assertEquals(NewsPresenter.AUTO_REFRESH_TIME_MS, periodicCallerStarts[0])
        view.assertNoErrors()
    }

    // When repository returns an error, refresh displays another one
    @Test
    fun refreshErrorTest() {
        val view = NewsView()
        overrideNewsRepository { throw NORMAL_ERROR }
        val presenter = NewsPresenter(view)
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertNull(view.newsList)
        assertEquals(2, view.displayedErrors.size)
        assertEquals(NORMAL_ERROR, view.displayedErrors[0])
        assertEquals(NORMAL_ERROR, view.displayedErrors[1])
    }

    // When different data are served after refresh, they are displayed
    @Test
    fun refreshTest() {
        val view = NewsView()
        val presenter = NewsPresenter(view)
        var firstRun = true
        overrideNewsRepository {
            if (firstRun) {
                firstRun = false
                NewsData(FAKE_NEWS_LIST_1)
            } else {
                NewsData(FAKE_NEWS_LIST_2_SORTED)
            }
        }
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertEquals(FAKE_NEWS_LIST_2_SORTED, view.newsList)
        assertEquals(2, view.timesShowListCalled)
        view.assertNoErrors()
    }

    // During refresh, swipeRefresh is displayed and loading is not
    @Test
    fun refreshDisplayTest() {
        val view = NewsView()
        val presenter = NewsPresenter(view)
        assertFalse(view.loading)
        assertFalse(view.refresh)
        var onCreateRun = true
        var timesRepositoryUsed = 0
        overrideNewsRepository {
            timesRepositoryUsed++
            if (onCreateRun) {
                assertTrue(view.loading)
                assertFalse(view.refresh)
                onCreateRun = false
            } else {
                assertFalse(view.loading)
                assertTrue(view.refresh)
            }
            NewsData(FAKE_NEWS_LIST_1)
        }
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertEquals(2, timesRepositoryUsed)
        assertFalse(view.loading)
        assertFalse(view.refresh)
        assertEquals(FAKE_NEWS_LIST_1, view.newsList)
        view.assertNoErrors()
    }

    // News are displayed in occurrence order - from newest to oldest
    @Test
    fun newsOrderTest() {
        val view = NewsView()
        val presenter = NewsPresenter(view)
        overrideNewsRepository { NewsData(FAKE_NEWS_LIST_2_UNSORTED) }
        // When
        presenter.onCreate()
        // Then
        assertEquals(FAKE_NEWS_LIST_2_SORTED, view.newsList)
        view.assertNoErrors()
    }

    // When nothing changed, list is not called again
    @Test
    fun refreshNoChangesTest() {
        val view = NewsView()
        val presenter = NewsPresenter(view)
        overrideNewsRepository { NewsData(FAKE_NEWS_LIST_1) }
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertEquals(FAKE_NEWS_LIST_1, view.newsList)
        assertEquals(1, view.timesShowListCalled)
        view.assertNoErrors()
    }

    private fun overrideNewsRepository(getNewsData: () -> NewsData) {
        NewsRepository.override = object : NewsRepository {
            suspend override fun getNewsData(): NewsData = getNewsData()
        }
    }

    private fun overridePeriodicCaller(start: (timeMillis: Long, callback: () -> Unit) -> Cancellable) {
        PeriodicCaller.override = object : PeriodicCaller {
            override fun start(timeMillis: Long, callback: () -> Unit) = start(timeMillis, callback)
        }
    }

    private fun NewsView() = object : NewsView {
        override var loading: Boolean = false
        override var refresh: Boolean = false
        var newsList: List<News>? = null
        var displayedErrors: List<Throwable> = emptyList()
        var timesShowListCalled = 0

        override fun showList(news: List<News>) {
            timesShowListCalled++
            newsList = news
        }

        override fun showError(error: Throwable) {
            displayedErrors += error
        }

        override fun logError(error: Throwable) {
            throw error
        }

        fun assertNoErrors() {
            displayedErrors.forEach { throw it }
            assertEquals(0, displayedErrors.size)
        }
    }

    private fun Cancellable() = object : Cancellable {}

    companion object {
        val FAKE_NEWS_1 = News(1, "Some title", "Description", "Image url", "Url", "2018-10-13T12:00:01".parseDate())
        val FAKE_NEWS_2 = News(2, "Some title 2", "Description 2", "Image url 2", "Url 2", "2018-10-12T12:00:01".parseDate())
        val FAKE_NEWS_LIST_1 = listOf(FAKE_NEWS_1)
        val FAKE_NEWS_LIST_2_SORTED = listOf(FAKE_NEWS_1, FAKE_NEWS_2)
        val FAKE_NEWS_LIST_2_UNSORTED = listOf(FAKE_NEWS_2, FAKE_NEWS_1)
        val NORMAL_ERROR = Error()
    }
}