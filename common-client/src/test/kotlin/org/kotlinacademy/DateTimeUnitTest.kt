package org.kotlinacademy

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DateTimeUnitTest: BaseUnitTest() {

    @JsName("twoSideConversionTest")
    @Test
    fun `Two way conversion should give the same result`() {
        val dateFormatted = "2018-10-12T12:00:01"
        assertEquals(dateFormatted, dateFormatted.parseDateTime().toDateFormatString())
    }

    @JsName("datetimeParsingTest")
    @Test
    fun `Ordering is correct after parse`() {
        val date1 = "2018-10-12T12:00:01".parseDateTime()
        val date2 = "2018-10-12T12:10:01".parseDateTime()
        val date3 = "2018-10-12T13:00:01".parseDateTime()
        assertTrue(date1 < date2)
        assertTrue(date2 < date3)
        assertTrue(date1 < date3)
    }
}