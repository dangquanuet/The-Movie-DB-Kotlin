package com.example.moviedb.di

import android.content.Context
import com.example.moviedb.BuildConfig
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.MockApi
import com.example.moviedb.data.remote.RxErrorHandlingCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpCache(get()) }
    single(name = "logging") { createLoggingInterceptor() }
    single(name = "header") { createHeaderInterceptor() }
    single { createOkHttpClient(get(), get(name = "logging"), get(name = "header")) }
    single { createAppRetrofit(get(), get()) }
    single { createApiService(get()) }
    single { RxErrorHandlingCallAdapterFactory() }
}

const val TIMEOUT = 10

fun createOkHttpCache(context: Context): Cache =
    Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

fun createLoggingInterceptor(): Interceptor =
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

fun createHeaderInterceptor(): Interceptor =
    Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url().newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMBD_API_KEY)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
//            .header("Content-Type", "application/json")
//            .header("X-App-Secret", "1234567890")
//            .header("Authorization", userRepositoryImpl.getAccessToken())
            .method(request.method(), request.body())
            .build()
        chain.proceed(newRequest)
    }

fun createOkHttpClient(
    cache: Cache,
    logging: Interceptor,
    header: Interceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .cache(cache)
        .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .addInterceptor(header)
        .addInterceptor(logging)
        .build()

fun createAppRetrofit(
    okHttpClient: OkHttpClient,
    rxErrorHandlingCallAdapterFactory: RxErrorHandlingCallAdapterFactory
): Retrofit =
    Retrofit.Builder()
//        .addCallAdapterFactory(rxErrorHandlingCallAdapterFactory)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()


fun createApiService(retrofit: Retrofit): ApiService =
    if (BuildConfig.MOCK_DATA) MockApi()
    else retrofit.create(ApiService::class.java)