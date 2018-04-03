package com.quanda.moviedb.data.source.local

import android.content.Context
import android.content.SharedPreferences
import com.quanda.moviedb.utils.GsonUtils

class SharedPreferenceApi private constructor(context: Context) {

    companion object {
        private const val PREFS_NAME = "MovieDBSharedPreferences"
        private var INSTANCE: SharedPreferenceApi? = null

        fun getInstance(context: Context): SharedPreferenceApi {
            if (INSTANCE == null) {
                INSTANCE = SharedPreferenceApi(context)
            }
            return INSTANCE as SharedPreferenceApi
        }
    }

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    operator fun <T> get(key: String, clazz: Class<T>): T? {
        if (clazz == String::class.java) {
            return sharedPreferences.getString(key, "") as T
        } else if (clazz == Boolean::class.java) {
            return java.lang.Boolean.valueOf(sharedPreferences.getBoolean(key, false)) as T
        } else if (clazz == Float::class.java) {
            return java.lang.Float.valueOf(sharedPreferences.getFloat(key, 0f)) as T
        } else if (clazz == Int::class.java) {
            return Integer.valueOf(sharedPreferences.getInt(key, 0)) as T
        } else if (clazz == Long::class.java) {
            return java.lang.Long.valueOf(sharedPreferences.getLong(key, 0)) as T
        } else {
            return GsonUtils.stringToObject(sharedPreferences.getString(key, ""), clazz) as T
        }
    }

    operator fun <T> get(key: String, clazz: Class<T>, defValue: T): T? {
        if (clazz == String::class.java) {
            return sharedPreferences.getString(key, defValue as String) as T
        } else if (clazz == Boolean::class.java) {
            return java.lang.Boolean.valueOf(
                    sharedPreferences.getBoolean(key, defValue as Boolean)) as T
        } else if (clazz == Float::class.java) {
            return java.lang.Float.valueOf(sharedPreferences.getFloat(key, defValue as Float)) as T
        } else if (clazz == Int::class.java) {
            return Integer.valueOf(sharedPreferences.getInt(key, defValue as Int)) as T
        } else if (clazz == Long::class.java) {
            return java.lang.Long.valueOf(sharedPreferences.getLong(key, defValue as Long)) as T
        } else {
            return GsonUtils.stringToObject(sharedPreferences.getString(key, defValue as String),
                    clazz)
        }
    }

    fun <T> put(key: String, data: T) {
        val editor = sharedPreferences.edit()
        if (data is String) {
            editor.putString(key, data as String)
        } else if (data is Boolean) {
            editor.putBoolean(key, data as Boolean)
        } else if (data is Float) {
            editor.putFloat(key, data as Float)
        } else if (data is Int) {
            editor.putInt(key, data as Int)
        } else if (data is Long) {
            editor.putLong(key, data as Long)
        } else {
            editor.putString(key, GsonUtils.objectToString(data as Unit))
        }
        editor.apply()
    }
}