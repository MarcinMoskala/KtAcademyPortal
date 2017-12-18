@file:Suppress("IllegalIdentifier")

package org.kotlinacademy

import org.kotlinacademy.common.Cancellable
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.presentation.news.NewsPresenter
import kotlin.test.Test
import kotlin.test.assertTrue

class BasePresenterUnitTest {

    @Test
    fun `BasePresenter is cancelling all jobs during onDestroy`() {
        val jobs = (1..10).map { makeJob() }
        val presenter = object : BasePresenter() {
            fun addJobs(jobs: List<Cancellable>) {
                this.jobs += jobs
            }
        }
        presenter.addJobs(jobs)
        // When
        presenter.onDestroy()
        // Then
        assertTrue(jobs.all { it.cancelled })
    }

    private fun makeJob() = object : Cancellable {
        var cancelled = false
        override fun cancel() {
            cancelled = true
        }
    }
}