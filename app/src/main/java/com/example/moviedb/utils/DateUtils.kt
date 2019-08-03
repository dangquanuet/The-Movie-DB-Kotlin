package com.example.moviedb.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * convert string to date
 * if string is blank or format is blank then return null
 * if string cannot be parsed then return null
 * else return date
 */
fun String.toDate(
    format: String, locale: Locale = Locale.getDefault()
): Date? {
    if (this.isBlank() || format.isBlank()) return null
    return try {
        SimpleDateFormat(format, locale).parse(this)
    } catch (e: Exception) {
        e.safeLog()
        null
    }
}

/**
 * convert string to time long milliseconds
 * use function string to date
 */
fun String.toTimeLong(
    format: String, locale: Locale = Locale.getDefault()
): Long? = toDate(format, locale)?.time

/**
 * convert time long milliseconds to string with predefined format
 * if format is blank return null
 * if format is not java date time format then catch Exception and return null
 * else return formatted string
 */
fun Long.toTimeString(
    format: String, locale: Locale = Locale.getDefault()
): String? {
    if (format.isBlank()) return null
    return try {
        SimpleDateFormat(format, locale).format(Date(this))
    } catch (e: Exception) {
        e.safeLog()
        null
    }
}

/**
 * change time string format from oldFormat to newFormat
 * if string or oldFormat or newFormat is blank then return null
 * if oldFormat/newFormat is illegal then catch exception and return null
 * else return string
 */
fun String.changeTimeFormat(
    oldFormat: String, newFormat: String, locale: Locale = Locale.getDefault()
): String? {
    if (this.isBlank() || oldFormat.isBlank() || newFormat.isBlank()) return null
    return try {
        val simpleDateFormat = SimpleDateFormat(oldFormat, locale)
        val date = simpleDateFormat.parse(this)
        simpleDateFormat.applyPattern(newFormat)
        if (date != null) simpleDateFormat.format(date)
        else null
    } catch (e: Exception) {
        e.safeLog()
        null
    }
}

/**
 * convert date to time string
 * if format is wrong or illegal then catch exception and return null
 * else return string
 */
fun Date.toTimeString(format: String, locale: Locale = Locale.getDefault()): String? {
    return if (format.isBlank()) null
    else try {
        SimpleDateFormat(format, locale).format(this)
    } catch (e: Exception) {
        e.safeLog()
        null
    }
}

/**
 * get current date time
 */
fun getCurrentDateTime(): Date = Calendar.getInstance().time

/**
 * convert date to calendar
 */
fun Date.toCalendar(): Calendar {
    return Calendar.getInstance().let {
        it.time = this
        it
    }
}

/**
 * get previous month of this date
 */
fun Date.getPreviousMonth(): Date {
    return Calendar.getInstance().let {
        it.time = this
        it.add(Calendar.MONTH, -1)
        it.time
    }
}

/**
 * get next month of this date
 */
fun Date.getNextMonth(): Date {
    return Calendar.getInstance().let {
        it.time = this
        it.add(Calendar.MONTH, 1)
        it.time
    }
}

/**
 * get previous day of this date
 */
fun Date.getPreviousDay(): Date {
    return Calendar.getInstance().let {
        it.time = this
        it.add(Calendar.DAY_OF_MONTH, -1)
        it.time
    }
}

/**
 * get next day of this date
 */
fun Date.getNextDay(): Date {
    return Calendar.getInstance().let {
        it.time = this
        it.add(Calendar.DAY_OF_MONTH, 1)
        it.time
    }
}
