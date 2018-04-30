package org.kotlinacademy

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.kotlinacademy.presentation.news.OfflineNewsPresenter
import org.kotlinacademy.presentation.news.OfflineNewsView
import org.kotlinacademy.respositories.NewsLocalRepository
import kotlin.test.Test

class OfflineNewsPresenterUnitTest : BaseUnitTest() {

    @Test
    fun `When new data are displayed, they are saved in local storage`() {
        // Given
        val view: OfflineNewsView = mockk(relaxed = true)
        val repo: NewsLocalRepository = mockk(relaxed = true)
        val presenter = OfflineNewsPresenter(view, repo)

        // When
        presenter.onNewsLoaded(someNewsList)

        // When
        verify {
            repo.setNews(someNewsList)
        }
    }

    @Test
    fun `When there is no network, saved data are displayed`() {
        // Given
        val view: OfflineNewsView = mockk(relaxed = true)
        val repo: NewsLocalRepository = mockk(relaxed = true)
        every { repo.getNews() } returns someNewsList
        val presenter = OfflineNewsPresenter(view, repo)

        // When
        presenter.onNoInternet()

        // When
        verify {
            view.showListOffline(someNewsList)
        }
    }

    @Test
    fun `If there is no saved data, presenter doesn't display anything`() {
        // Given
        val view: OfflineNewsView = mockk(relaxed = true)
        val repo: NewsLocalRepository = mockk(relaxed = true)
        every { repo.getNews() } returns null
        val presenter = OfflineNewsPresenter(view, repo)

        // When
        presenter.onNoInternet()

        // When
        verify(inverse = true) {
            view.showListOffline(any())
        }
    }
}