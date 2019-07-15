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
    fun stringToDate_stringIsEmpty() {
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
    fun stringToDate_stringIsBlank() {
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
    fun stringToDate_formatIsEmpty() {
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
    fun stringToDate_formatIsBlank() {
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
    fun stringToDate_success() {
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
    fun stringToDate_formatIllegal() {
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
    fun stringToTimeLong_stringIsEmpty() {
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
    fun stringToTimeLong_stringIsBlank() {
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
    fun stringToTimeLong_formatIsEmpty() {
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
    fun stringToTimeLong_formatIsBlank() {
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
    fun stringToTimeLong_success() {
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
    fun stringToTimeLong_formatIllegal() {
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
    fun longToTimeString_formatIsEmpty() {
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
    fun longToTimeString_formatIsBlank() {
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
    fun longToTimeString_formatFull() {
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
    fun longToTimeString_formatShort() {
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
    fun longToTimeString_formatIllegal() {
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
    fun stringChangeTimeFormat_stringIsEmpty() {
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
    fun stringChangeTimeFormat_stringIsBlank() {
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
    fun stringChangeTimeFormat_oldFormatIsEmpty() {
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
    fun stringChangeTimeFormat_oldFormatIsBlank() {
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
    fun stringChangeTimeFormat_oldFormatIllegal() {
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
    fun stringChangeTimeFormat_newFormatIsEmpty() {
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
    fun stringChangeTimeFormat_newFormatIsBlank() {
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
    fun stringChangeTimeFormat_newFormatIllegal() {
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
    fun stringChangeTimeFormat_success() {
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
    fun dateToTimeString_formatIsEmpty() {
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
    fun dateToTimeString_formatIsBlank() {
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
    fun dateToTimeString_formatIllegal() {
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
    fun dateToTimeString_success() {
        assertEquals(
            timeString_ddMMyyyy,
            timeDate.toTimeString(format_ddMMyyyy)
        )
    }

    @Test
    fun dateToCalendar_success() {
        assertEquals(
            timeCalendar,
            timeDate.toCalendar()
        )
    }
}