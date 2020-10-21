package com.example.moviedb.data.local.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import javax.inject.Inject

class AppPrefs @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    val moshi: Moshi
) : PrefHelper {

    companion object {
        private const val FIRST_RUN = "FIRST_RUN"
    }

    override fun isFirstRun(): Boolean {
        val isFirstRun = sharedPreferences.getBoolean(FIRST_RUN, true)
        if (isFirstRun) {
            sharedPreferences.edit { putBoolean(FIRST_RUN, false) }
        }
        return isFirstRun
    }

    override fun remove(key: String) {
        sharedPreferences.edit {
            remove(key)
        }
    }

    override fun clear() {
        sharedPreferences.edit { clear() }
    }
}