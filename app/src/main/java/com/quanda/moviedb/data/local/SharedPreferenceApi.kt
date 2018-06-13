package com.quanda.moviedb.data.local

import android.content.Context
import android.content.SharedPreferences
import com.quanda.moviedb.utils.GsonUtils
import javax.inject.Inject
import javax.inject.Singleton

// top level fun or properties in kotlin = static in java
const val PREFS_NAME = "MovieDBSharedPreferences"

@Singleton
class SharedPreferenceApi @Inject constructor(context: Context) {

//    companion object {
//        private const val PREFS_NAME = "MovieDBSharedPreferences"
//    }

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Java way
     */
    fun <T> get(key: String, clazz: Class<T>): T {
        return when (clazz) {
            String::class.java -> sharedPreferences.getString(key, "") as T
            Boolean::class.java -> sharedPreferences.getBoolean(key, false) as T
            Float::class.java -> sharedPreferences.getFloat(key, 0f) as T
            Int::class.java -> sharedPreferences.getInt(key, 0) as T
            Long::class.java -> sharedPreferences.getLong(key, 0) as T
            else -> GsonUtils.stringToObject(sharedPreferences.getString(key, ""), clazz) as T
        }
    }

    /**
     * Kotlin way
     */
    inline fun <reified T> get(key: String): T {
        return when (T::class) {
            String::class -> get(key, "") as T
            Boolean::class -> get(key, false) as T
            Float::class -> get(key, 0.0) as T
            Int::class -> get(key, 0) as T
            Long::class -> get(key, 0) as T
            else -> GsonUtils.stringToObject(get(key, ""), T::class.java) as T
        }
    }

    fun <T> get(key: String, defValue: T): T {
        return when (defValue) {
            is String -> sharedPreferences.getString(key, defValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defValue) as T
            is Float -> sharedPreferences.getFloat(key, defValue) as T
            is Int -> sharedPreferences.getInt(key, defValue) as T
            is Long -> sharedPreferences.getLong(key, defValue) as T
            else -> defValue
        }
    }

    fun <T> put(key: String, data: T) {
        sharedPreferences.edit().apply {
            when (data) {
                is String -> putString(key, data)
                is Boolean -> putBoolean(key, data)
                is Float -> putFloat(key, data)
                is Int -> putInt(key, data)
                is Long -> putLong(key, data)
                else -> putString(key, GsonUtils.objectToString(data as Any))
            }
        }.apply()
    }

    fun remove(key: String) = sharedPreferences.edit().remove(key).apply()

    fun clear() = sharedPreferences.edit().clear().apply()
}