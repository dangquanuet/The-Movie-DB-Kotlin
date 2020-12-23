package com.example.moviedb.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateUtils {

    private val timeLongMilliseconds = 1563153912L * 1000
    private val timeDate = Date(timeLongMilliseconds)
    private val timeCalendar = Calendar.getInstance().apply { time = timeDate }
    private val timeString_yyyyMMdd_HHmmss = "2019-07-15 08:25:12"
    private val format_yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss"

    private val timeString_yyyyMMdd = "2019-07-15"
    private val format_yyyyMMdd = "yyyy-MM-dd"

    private val timeString_ddMMyyyy = "15-07-2019"
    private val format_ddMMyyyy = "dd-MM-yyyy"
    private val format_illegal = "bb"
    private val emptyString = ""
    private val blankString = "       "

    /**
     * string is empty
     * return null
     */
    @Test
    fun `string_toDate, when string is empty then return null`() {
        assertEquals(
            null,
            emptyString.toDate(format_yyyyMMdd)
        )
    }

    /**
     * string is blank
     * return null
     */
    @Test
    fun `string_toDate, when string ss blank then return null`() {
        assertEquals(
            null,
            blankString.toDate(format_yyyyMMdd)
        )
    }

    /**
     * format is empty
     * return null
     */
    @Test
    fun `string_toDate, when format is empty then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.toDate(emptyString)
        )
    }

    /**
     * format is blank
     * return null
     */
    @Test
    fun `string_toDate, when format is blank then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.toDate(blankString)
        )
    }

    /**
     * string and format correct
     * return success
     */
    @Test
    fun `string_toDate, when string and format are valid then return success`() {
        assertEquals(
            timeDate,
            timeString_yyyyMMdd_HHmmss.toDate(format_yyyyMMdd_HHmmss)
        )
    }

    /**
     * format is not correct
     * return null
     */
    @Test
    fun `string_toDate, when format is illegal then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd_HHmmss.toDate(format_illegal)
        )
    }

    /**
     * string is empty
     * return null
     */
    @Test
    fun `string_toTimeLong, when string is empty then return null`() {
        assertEquals(
            null,
            emptyString.toTimeLong(format_yyyyMMdd)
        )
    }

    /**
     * string is blank
     * return null
     */
    @Test
    fun `string_toTimeLong, when string is blank then return null`() {
        assertEquals(
            null,
            blankString.toTimeLong(format_yyyyMMdd)
        )
    }

    /**
     * format is empty
     * return null
     */
    @Test
    fun `string_toTimeLong, when format is empty then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.toTimeLong(emptyString)
        )
    }

    /**
     * format is blank
     * return null
     */
    @Test
    fun `string_toTimeLong, when format is blank then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.toTimeLong(blankString)
        )
    }

    /**
     * string and format correct
     * return success
     */
    @Test
    fun `string_toTimeLong, when string and format are valid then return success`() {
        assertEquals(
            timeLongMilliseconds,
            timeString_yyyyMMdd_HHmmss.toTimeLong(format_yyyyMMdd_HHmmss)
        )
    }

    /**
     * format is not correct
     * return null
     */
    @Test
    fun `string_toTimeLong, when format is illegal then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd_HHmmss.toTimeLong(format_illegal)
        )
    }

    /**
     * format is empty
     * return null
     */
    @Test
    fun `long_toTimeString, when format is empty then return null`() {
        assertEquals(
            null,
            timeLongMilliseconds.toTimeString(emptyString)
        )
    }

    /**
     * format is blank
     * return null
     */
    @Test
    fun `long_toTimeString, when format is blank then return null`() {
        assertEquals(
            null,
            timeLongMilliseconds.toTimeString(blankString)
        )
    }

    /**
     * format correct
     * return success
     */
    @Test
    fun `long_toTimeString, when format is full then return string`() {
        assertEquals(
            timeString_yyyyMMdd_HHmmss,
            timeLongMilliseconds.toTimeString(format_yyyyMMdd_HHmmss)
        )
    }

    /**
     * format correct
     * return success
     */
    @Test
    fun `long_toTimeString, when format is short then return string`() {
        assertEquals(
            timeString_yyyyMMdd,
            timeLongMilliseconds.toTimeString(format_yyyyMMdd)
        )
    }

    /**
     * format is not java date time format
     * return null
     */
    @Test
    fun `long_toTimeString when format is illegal then return null`() {
        assertEquals(
            null,
            timeLongMilliseconds.toTimeString(format_illegal)
        )
    }

    /**
     * string is empty
     * return null
     */
    @Test
    fun `string_changeTimeFormat when string is empty then return null`() {
        assertEquals(
            null,
            emptyString.changeTimeFormat(format_yyyyMMdd, format_ddMMyyyy)
        )
    }

    /**
     * string is blank
     * return null
     */
    @Test
    fun `string_changeTimeFormat when string is blank then return null`() {
        assertEquals(
            null,
            blankString.changeTimeFormat(format_yyyyMMdd, format_ddMMyyyy)
        )
    }

    /**
     * oldFormat is empty
     * return null
     */
    @Test
    fun `string changeTimeFormat when oldFormat is empty then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.changeTimeFormat(emptyString, format_ddMMyyyy)
        )
    }

    /**
     * oldFormat is blank
     * return null
     */
    @Test
    fun `string_changeTimeFormat when oldFormat is blank then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.changeTimeFormat(blankString, format_ddMMyyyy)
        )
    }

    /**
     * oldFormat is illegal
     * return null
     */
    @Test
    fun `string_changeTimeFormat when oldFormat is illegal then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.changeTimeFormat(format_illegal, format_yyyyMMdd)
        )
    }

    /**
     * newFormat is empty
     * return null
     */
    @Test
    fun `string_changeTimeFormat when newFormat is empty then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.changeTimeFormat(format_yyyyMMdd, emptyString)
        )
    }

    /**
     * newFormat is blank
     * return null
     */
    @Test
    fun `string_changeTimeFormat when newFormat is blank then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.changeTimeFormat(format_yyyyMMdd, blankString)
        )
    }

    /**
     * newFormat is blank
     * return null
     */
    @Test
    fun `string_changeTimeFormat when newFormat is illegal then return null`() {
        assertEquals(
            null,
            timeString_yyyyMMdd.changeTimeFormat(format_yyyyMMdd, format_illegal)
        )
    }

    /**
     * string, oldFormat, newFormat correct
     * return success
     */
    @Test
    fun `string_changeTimeFormat when string and params are valid then return success`() {
        assertEquals(
            timeString_ddMMyyyy,
            timeString_yyyyMMdd.changeTimeFormat(format_yyyyMMdd, format_ddMMyyyy)
        )
    }

    /**
     * format is empty
     * return null
     */
    @Test
    fun `date_toTimeString when format is empty then return null`() {
        assertEquals(
            null,
            timeDate.toTimeString(emptyString)
        )
    }

    /**
     * format is blank
     * return null
     */
    @Test
    fun `date_toTimeString when format is blank then return null`() {
        assertEquals(
            null,
            timeDate.toTimeString(blankString)
        )
    }

    /**
     * format is blank
     * return null
     */
    @Test
    fun `date_toTimeString when format is illegal then return null`() {
        assertEquals(
            null,
            timeDate.toTimeString(format_illegal)
        )
    }

    /**
     * format is correct
     * return success
     */
    @Test
    fun `date_toTimeString when format is valid then return success`() {
        assertEquals(
            timeString_ddMMyyyy,
            timeDate.toTimeString(format_ddMMyyyy)
        )
    }

    @Test
    fun `date_toCalendar when format is valid then return success`() {
        assertEquals(
            timeCalendar,
            timeDate.toCalendar()
        )
    }
}
