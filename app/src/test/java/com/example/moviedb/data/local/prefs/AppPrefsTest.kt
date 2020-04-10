/**
 * gkc_hash_code : 01DTTE3W3Z4Y56WHYW8SYCTXMK
 */
package com.example.moviedb.data.local.prefs

import android.content.SharedPreferences
import com.example.moviedb.data.local.pref.AppPrefs
import com.example.moviedb.data.local.pref.PrefHelper
import com.example.moviedb.mock
import com.google.gson.Gson
import org.junit.Before

class AppPrefsTest {
    private val sharedPreferences = mock<SharedPreferences>()
    private lateinit var gson: Gson
    private lateinit var pref: PrefHelper

    @Before
    fun setup() {
        gson = Gson()
        pref = AppPrefs(
            gson = gson,
            sharedPreferences = sharedPreferences
        )
    }

}
