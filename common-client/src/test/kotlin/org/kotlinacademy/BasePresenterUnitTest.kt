package org.kotlinacademy

import kotlin.test.Test

class BasePresenterUnitTest: BaseUnitTest() {

    @JsName("cancellingJobTest")
    @Test
    fun `BasePresenter is cancelling all jobs during onDestroy`() {
        // TODO
//        val jobs = (1..10).map { makeJob() }
//        val presenter = object : BasePresenter() {
//            fun addJobs(jobs: List<Job>) {
//                this.jobs += jobs
//            }
//        }
//        presenter.addJobs(jobs)
//        // When
//        presenter.onDestroy()
//        // Then
//        assertTrue(jobs.all { it.cancelled })
    }

//    private fun makeJob() = object : Job {
//        var cancelled = false
//        override val isCancelled: Boolean
//            get() = cancelled
//        override fun cancel(cause: Throwable?): Boolean {
//            cancelled = true
//            return true
//        }
//    }
}