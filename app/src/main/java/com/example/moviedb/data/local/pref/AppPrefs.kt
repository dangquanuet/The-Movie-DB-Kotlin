package com.example.moviedb.data.local.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppPrefs @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val moshi: Moshi
) : PrefHelper {

    private object Key {
        val FIRST_RUN = booleanPreferencesKey("first_run")
    }

    override suspend fun isFirstRun(): Flow<Boolean> =
        dataStore.data.map { pref ->
            val firstRun = pref[Key.FIRST_RUN] ?: true
            if (firstRun) {
                dataStore.edit { pref ->
                    pref[Key.FIRST_RUN] = false
                }
            }
            firstRun
        }

    override suspend fun clear() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }
}