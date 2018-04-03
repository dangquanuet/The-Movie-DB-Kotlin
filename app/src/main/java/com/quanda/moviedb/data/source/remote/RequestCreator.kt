package com.quanda.moviedb.data.source.remote

import com.quanda.moviedb.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RequestCreator {

    companion object {
        const val CONNECTION_TIMEOUT: Long = 30

        private val apiInstance = getApiInstance("")

        fun getRequest() = apiInstance

        fun getRequest(token: String) = getApiInstance(token)

        private fun getApiInstance(token: String): ApiService {
            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            httpClient.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            httpClient.addInterceptor(getHttpLoggingInterceptor())

            return Retrofit.Builder().addConverterFactory(
                    GsonConverterFactory.create()).addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()).baseUrl(BuildConfig.BASE_URL).client(
                    httpClient.build()).build().create(
                    apiInstance.javaClass)
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return interceptor
        }
    }


}