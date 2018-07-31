package com.quanda.moviedb.data.local.pref

interface PrefHelper {

    fun isFirstRun(): Boolean

    fun clear()

}