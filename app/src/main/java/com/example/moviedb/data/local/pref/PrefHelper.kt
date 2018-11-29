package com.example.moviedb.data.local.pref

interface PrefHelper {

    fun isFirstRun(): Boolean

    fun clear(key: String)

    fun clear()

}