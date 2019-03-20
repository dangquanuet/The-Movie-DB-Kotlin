package com.example.moviedb.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(
    format: String, locale: Locale = Locale.getDefault()
): Date? {
    if (this.isBlank() || format.isBlank()) return null
    return try {
        SimpleDateFormat(format, locale).parse(this)
    } catch (e: Exception) {
        e.safePLog()
        null
    }
}

fun String.toTimeLong(
    format: String, locale: Locale = Locale.getDefault()
): Long? = toDate(format, locale)?.time

fun Long.toTimeString(
    format: String, locale: Locale = Locale.getDefault()
): String? {
    if (format.isBlank()) return null
    return SimpleDateFormat(format, locale).format(Date(this))
}

fun String.changeTimeFormat(
    oldFormat: String, newFormat: String, locale: Locale = Locale.getDefault()
): String? {
    if (this.isBlank() || oldFormat.isBlank() || newFormat.isBlank()) return null
    return try {
        val simpleDateFormat = SimpleDateFormat(oldFormat, locale)
        val date = simpleDateFormat.parse(this)
        simpleDateFormat.applyPattern(newFormat)
        simpleDateFormat.format(date)
    } catch (e: Exception) {
        e.safePLog()
        null
    }
}

fun Date.toTimeString(format: String, locale: Locale = Locale.getDefault()): String? {
    return if (format.isBlank()) null
    else SimpleDateFormat(format, locale).format(this)
}

fun getCurrentDateTime(): Date? = Calendar.getInstance().time

fun Date.toCalendar(): Calendar? {
    return Calendar.getInstance()?.let {
        it.time = this
        it
    }
}

fun Date.getPreviousMonth(): Date? {
    return Calendar.getInstance()?.let {
        it.time = this
        it.add(Calendar.MONTH, -1)
        it.time
    }
}

fun Date.getNextMonth(): Date? {
    return Calendar.getInstance()?.let {
        it.time = this
        it.add(Calendar.MONTH, 1)
        it.time
    }
}

fun Date.getPreviousDay(): Date? {
    return Calendar.getInstance()?.let {
        it.time = this
        it.add(Calendar.DAY_OF_MONTH, -1)
        it.time
    }
}

fun Date.getNextDay(): Date? {
    return Calendar.getInstance()?.let {
        it.time = this
        it.add(Calendar.DAY_OF_MONTH, 1)
        it.time
    }
}
