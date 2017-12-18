@file:Suppress("IllegalIdentifier")

package org.kotlinacademy

import org.junit.Assert.*
import org.junit.Test
import org.kotlinacademy.common.Cancellable
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.presentation.news.NewsPresenter

class DateTimeUnitTest {

    @Test
    fun `Two way conversion should give the same result`() {
        val dateFormatted = "2018-10-12T12:00:01"
        assertEquals(dateFormatted, dateFormatted.parseDate().toDateFormatString())
    }

    @Test
    fun `Ordering is correct after parse`() {
        val date1 = "2018-10-12T12:00:01".parseDate()
        val date2 = "2018-10-12T12:10:01".parseDate()
        val date3 = "2018-10-12T13:00:01".parseDate()
        assertTrue(date1 < date2)
        assertTrue(date2 < date3)
        assertTrue(date1 < date3)
    }
}