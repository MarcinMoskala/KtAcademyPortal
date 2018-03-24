package org.kotlinacademy

import org.kotlinacademy.data.Article
import org.kotlinacademy.data.ArticleData
import org.kotlinacademy.data.News
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.respositories.NewsRepository
import kotlin.test.*

class NewsPresenterUnitTest : BaseUnitTest() {

    @BeforeTest
    fun setUp() {
        overrideNewsRepository({ NewsData(emptyList(), emptyList(), emptyList()) })
    }

    @JsName("gettingAndDisplayingTest")
    @Test
    fun `When onCreate, loads and displays list of news`() {
        val view = NewsView()
        overrideNewsRepository { NewsData(FAKE_NEWS_LIST_1, emptyList(), emptyList()) }
        val presenter = NewsPresenter(view)
        // When
        presenter.onCreate()
        // Then
        assertEquals(FAKE_NEWS_LIST_1, view.articleList)
        view.assertNoErrors()
    }

    @JsName("loaderTest")
    @Test
    fun `When onCreate, loader is displayed during repository usage but not before and after onCreate operation`() {
        val view = NewsView()

        var repositoryUsed = false
        overrideNewsRepository {
            assertTrue(view.loading)
            repositoryUsed = true
            NewsData(FAKE_NEWS_LIST_1, emptyList(), emptyList())
        }
        val presenter = NewsPresenter(view)
        assertFalse(view.loading)
        // When
        presenter.onCreate()
        // Then
        assertTrue(repositoryUsed)
        assertFalse(view.loading)
        assertEquals(FAKE_NEWS_LIST_1, view.articleList)
        view.assertNoErrors()
    }

    @JsName("errorTest")
    @Test
    fun `When repository returns error, it is shown on view`() {
        val view = NewsView()
        overrideNewsRepository { throw NORMAL_ERROR }
        val presenter = NewsPresenter(view)
        // When
        presenter.onCreate()
        // Then
        assertNull(view.articleList)
        assertEquals(1, view.displayedErrors.size)
        assertEquals(NORMAL_ERROR, view.displayedErrors[0])
    }

    @JsName("refreshErrorTest")
    @Test
    fun `When repository returns an error, refresh displays another one`() {
        val view = NewsView()
        overrideNewsRepository { throw NORMAL_ERROR }
        val presenter = NewsPresenter(view)
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertNull(view.articleList)
        assertEquals(2, view.displayedErrors.size)
        assertEquals(NORMAL_ERROR, view.displayedErrors[0])
        assertEquals(NORMAL_ERROR, view.displayedErrors[1])
    }

    @JsName("refreshTest")
    @Test
    fun `When different data are served after refresh, they are displayed`() {
        val view = NewsView()
        val presenter = NewsPresenter(view)
        var firstRun = true
        overrideNewsRepository {
            if (firstRun) {
                firstRun = false
                NewsData(FAKE_NEWS_LIST_1, emptyList(), emptyList())
            } else {
                NewsData(FAKE_NEWS_LIST_2_SORTED, emptyList(), emptyList())
            }
        }
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertEquals(FAKE_NEWS_LIST_2_SORTED, view.articleList)
        assertEquals(2, view.timesShowListCalled)
        view.assertNoErrors()
    }

    @JsName("refreshDisplayTest")
    @Test
    fun `During refresh, swipeRefresh is displayed and loading is not`() {
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
            NewsData(FAKE_NEWS_LIST_1, emptyList(), emptyList())
        }
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertEquals(2, timesRepositoryUsed)
        assertFalse(view.loading)
        assertFalse(view.refresh)
        assertEquals(FAKE_NEWS_LIST_1, view.articleList)
        view.assertNoErrors()
    }

    @JsName("newsOrderTest")
    @Test
    fun `News are displayed in occurrence order - from newest to oldest`() {
        val view = NewsView()
        val presenter = NewsPresenter(view)
        overrideNewsRepository { NewsData(FAKE_NEWS_LIST_2_UNSORTED, emptyList(), emptyList()) }
        // When
        presenter.onCreate()
        // Then
        assertEquals(FAKE_NEWS_LIST_2_SORTED, view.articleList)
        view.assertNoErrors()
    }

    @JsName("refreshNoChangesTest")
    @Test
    fun `When nothing changed, list is not called again`() {
        val view = NewsView()
        val presenter = NewsPresenter(view)
        overrideNewsRepository { NewsData(FAKE_NEWS_LIST_1, emptyList(), emptyList()) }
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertEquals(FAKE_NEWS_LIST_1, view.articleList)
        assertEquals(1, view.timesShowListCalled)
        view.assertNoErrors()
    }

    private fun overrideNewsRepository(getNewsData: () -> NewsData) {
        NewsRepository.mock = object : NewsRepository {
            suspend override fun getNewsData(): NewsData = getNewsData()
        }
    }

    private fun NewsView() = object : NewsView {
        override var loading: Boolean = false
        override var refresh: Boolean = false
        var articleList: List<News>? = null
        var displayedErrors: List<Throwable> = emptyList()
        var timesShowListCalled = 0

        override fun showList(news: List<News>, newsData: NewsData) {
            timesShowListCalled++
            articleList = news.filterIsInstance<Article>()
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

    private fun Cancellable() = object {}

    companion object {
        val FAKE_NEWS_1 = Article(1, ArticleData("Some title", "Description", "Image url", "Url", "2018-10-13T12:00:01".parseDateTime()))
        val FAKE_NEWS_2 = Article(2, ArticleData("Some title 2", "Description 2", "Image url 2", "Url 2", "2018-10-12T12:00:01".parseDateTime()))
        val FAKE_NEWS_LIST_1 = listOf(FAKE_NEWS_1)
        val FAKE_NEWS_LIST_2_SORTED = listOf(FAKE_NEWS_1, FAKE_NEWS_2)
        val FAKE_NEWS_LIST_2_UNSORTED = listOf(FAKE_NEWS_2, FAKE_NEWS_1)
        val NORMAL_ERROR = Throwable()
    }
}