package org.kotlinacademy

import org.kotlinacademy.data.*
import org.kotlinacademy.presentation.news.NewsPresenter
import org.kotlinacademy.presentation.news.NewsView
import org.kotlinacademy.respositories.NewsRepository
import kotlin.test.*

class NewsPresenterUnitTest : BaseUnitTest() {

    @JsName("gettingAndDisplayingTest")
    @Test
    fun `When onCreate, loads and displays list of news`() {
        val view = NewsView()
        val repo = newsRepository { someNewsData }
        val presenter = NewsPresenter(Unconfined, view, repo)
        // When
        presenter.onCreate()
        // Then
        assertEquals(someNewsData.articles, view.newsData?.filterIsInstance<Article>())
        assertEquals(someNewsData.infos, view.newsData?.filterIsInstance<Info>())
        assertEquals(someNewsData.puzzlers, view.newsData?.filterIsInstance<Puzzler>())
        assertEquals(5, view.newsData?.size)
        view.assertNoErrors()
    }

    @JsName("loaderTest")
    @Test
    fun `When onCreate, loader is displayed during repository usage but not before and after onCreate operation`() {
        val view = NewsView()

        var repositoryUsed = false
        val repo = newsRepository {
            assertTrue(view.loading)
            repositoryUsed = true
            someNewsData
        }
        val presenter = NewsPresenter(Unconfined, view, repo)
        assertFalse(view.loading)
        // When
        presenter.onCreate()
        // Then
        assertTrue(repositoryUsed)
        assertFalse(view.loading)
        view.assertNoErrors()
    }

    @JsName("errorTest")
    @Test
    fun `When repository returns error, it is shown on view`() {
        val view = NewsView()
        val repo = newsRepository { throw someError }
        val presenter = NewsPresenter(Unconfined, view, repo)
        // When
        presenter.onCreate()
        // Then
        assertNull(view.newsData)
        assertEquals(1, view.displayedErrors.size)
        assertEquals(someError, view.displayedErrors[0])
    }

    @JsName("refreshErrorTest")
    @Test
    fun `When repository returns an error, refresh displays another one`() {
        val view = NewsView()
        val repo = newsRepository { throw someError }
        val presenter = NewsPresenter(Unconfined, view, repo)
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertNull(view.newsData)
        assertEquals(2, view.displayedErrors.size)
        assertEquals(someError, view.displayedErrors[0])
        assertEquals(someError, view.displayedErrors[1])
    }

    @JsName("refreshTest")
    @Test
    fun `When different data are served after refresh, they are displayed`() {
        val view = NewsView()
        var firstRun = true
        val repo = newsRepository {
            if (firstRun) {
                firstRun = false
                someNewsData
            } else {
                NewsData(someArticlesList2Sorted, emptyList(), emptyList())
            }
        }
        val presenter = NewsPresenter(Unconfined, view, repo)
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertEquals(someArticlesList2Sorted, view.newsData)
        assertEquals(2, view.timesShowListCalled)
        view.assertNoErrors()
    }

    @JsName("refreshDisplayTest")
    @Test
    fun `During refresh, swipeRefresh is displayed and loading is not`() {
        val view = NewsView()
        assertFalse(view.loading)
        assertFalse(view.refresh)
        var onCreateRun = true
        var timesRepositoryUsed = 0
        val repo = newsRepository {
            timesRepositoryUsed++
            if (onCreateRun) {
                assertTrue(view.loading)
                assertFalse(view.refresh)
                onCreateRun = false
            } else {
                assertFalse(view.loading)
                assertTrue(view.refresh)
            }
            someNewsData
        }
        val presenter = NewsPresenter(Unconfined, view, repo)
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertEquals(2, timesRepositoryUsed)
        assertFalse(view.loading)
        assertFalse(view.refresh)
        view.assertNoErrors()
    }

    @JsName("newsOrderTest")
    @Test
    fun `News order test`() {
        val view = NewsView()
        val someNewsData = NewsData(
                articles = listOf(someArticle1, someArticle2),
                infos = listOf(someInfo),
                puzzlers = listOf(somePuzzler2, somePuzzler)
        )
        val correctOrder = listOf(someInfo, someArticle1, somePuzzler, someArticle2, somePuzzler2)
        val repo = newsRepository { someNewsData }
        val presenter = NewsPresenter(Unconfined, view, repo)
        // When
        presenter.onCreate()
        // Then
        assertEquals(correctOrder, view.newsData)
        view.assertNoErrors()
    }

    @JsName("refreshNoChangesTest")
    @Test
    fun `When nothing changed, list is not called again`() {
        val view = NewsView()
        val repo = newsRepository { someNewsData }
        val presenter = NewsPresenter(Unconfined, view, repo)
        // When
        presenter.onCreate()
        presenter.onRefresh()
        // Then
        assertEquals(1, view.timesShowListCalled)
        view.assertNoErrors()
    }

    @JsName("refreshAfterCleanChangesTest")
    @Test
    fun `After cache clean, the same data is displayed`() {
        val view = NewsView()
        val repo = newsRepository { someNewsData }
        val presenter = NewsPresenter(Unconfined, view, repo)
        // When
        presenter.onCreate()
        presenter.cleanCache()
        presenter.onRefresh()
        // Then
        assertEquals(2, view.timesShowListCalled)
        view.assertNoErrors()
    }

    @JsName("sortingTest")
    @Test
    fun `News are sorted from nevest to oldest`() {
        val article1 = someArticle1.copy(dateTime = now)
        val info2 = someInfo.copy(dateTime = now - 100)
        val puzzler3 = somePuzzler.copy(dateTime = now - 1000)
        val article4 = someArticle2.copy(dateTime = now - 20000)
        val puzzler5 = somePuzzler2.copy(dateTime = now - 300000)

        checkNews(
                data = NewsData(
                        articles = listOf(article1, article4),
                        infos = listOf(info2),
                        puzzlers = listOf(puzzler5, puzzler3)
                ),
                result = listOf(article1, info2, puzzler3, article4, puzzler5)
        )
    }

    fun checkNews(data: NewsData, result: List<News>) {
        val view = NewsView()
        val repo = newsRepository { data }
        val presenter = NewsPresenter(Unconfined, view, repo)
        // When
        presenter.onCreate()
        // Then
        assertEquals(result, view.newsData)
    }

    private fun newsRepository(getNewsData: () -> NewsData) = object : NewsRepository {
        override suspend fun getNewsData(): NewsData = getNewsData()
    }

    private fun NewsView() = object : NewsView {
        override var loading: Boolean = false
        override var refresh: Boolean = false
        var newsData: List<News>? = null
        var displayedErrors: List<Throwable> = emptyList()
        var timesShowListCalled = 0

        override fun showList(news: List<News>) {
            timesShowListCalled++
            newsData = news
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
}