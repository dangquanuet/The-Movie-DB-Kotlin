package com.quanda.moviedb.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.util.*

class GsonUtils {

    companion object {
        private var INSTANCE: Gson? = null

        private fun getInstance(): Gson {
            if (INSTANCE == null) {
                INSTANCE = GsonBuilder().create()
            }
            return INSTANCE as Gson
        }

        fun objectToString(any: Any): String = getInstance().toJson(any)

        fun <T> stringToObject(json: String, claszz: Class<T>): T? {
            try {
                return getInstance().fromJson(json, claszz)
            } catch (ignored: JsonSyntaxException) {
                return null
            }
        }

        fun <T> stringToListObject(json: String, clazz: Class<T>): List<T> {
            return Arrays.asList(getInstance().fromJson(json, clazz))
        }
    }
}