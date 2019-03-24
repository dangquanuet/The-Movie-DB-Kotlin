package com.example.moviedb.data.local.pref

interface PrefHelper {

    fun isFirstRun(): Boolean

    fun remove(key: String)

    fun clear()

}