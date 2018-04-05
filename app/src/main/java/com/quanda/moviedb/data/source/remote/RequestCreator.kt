package com.quanda.moviedb.data.source.remote

import com.quanda.moviedb.BuildConfig
import okhttp3.*
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
            httpClient.addInterceptor(RequestInterceptor())

            return Retrofit.Builder().addConverterFactory(
                    GsonConverterFactory.create()).addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()).baseUrl(BuildConfig.BASE_URL).client(
                    httpClient.build()).build().create(
                    ApiService::class.java)
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return interceptor
        }
    }

    class RequestInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response? {
            val original: Request? = chain?.request()
            val originalHttpUrl: HttpUrl? = original?.url()
            val url: HttpUrl? = originalHttpUrl?.newBuilder()?.addQueryParameter("api_key",
                    BuildConfig.TMBD_API_KEY)?.build()
            val request: Request? = original?.newBuilder()?.url(url)?.build()
            return chain?.proceed(request)
        }
    }
}