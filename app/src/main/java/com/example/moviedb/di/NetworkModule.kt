package com.example.moviedb.di

import android.content.Context
import com.example.moviedb.BuildConfig
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.MockApi
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpCache(get()) }
    single(named("logging")) { createLoggingInterceptor() }
    single(named("header")) { createHeaderInterceptor() }
    single { createOkHttpClient(get(named("logging")), get(named("header"))) }
    single { createAppRetrofit(get()) }
    single { createApiService(get(), get(), get()) }
//    single { RxErrorHandlingFactory() }
    single { Mock(BuildConfig.MOCK_DATA) }
    single { MockApi() }
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
        val newUrl = request.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMBD_API_KEY)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
//            .header("Content-Type", "application/json")
//            .header("X-App-Secret", "1234567890")
//            .header("Authorization", userRepositoryImpl.getAccessToken())
            .method(request.method, request.body)
            .build()
        chain.proceed(newRequest)
    }

fun createOkHttpClient(
//    cache: Cache,
    logging: Interceptor,
    header: Interceptor
): OkHttpClient =
    OkHttpClient.Builder()
//        .cache(cache)
        .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .addInterceptor(header)
        .addInterceptor(logging)
        .addNetworkInterceptor(StethoInterceptor())
        .build()

fun createAppRetrofit(
    okHttpClient: OkHttpClient
//    rxErrorHandlingFactory: RxErrorHandlingFactory,
): Retrofit =
    Retrofit.Builder()
//        .addCallAdapterFactory(rxErrorHandlingFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()


fun createApiService(retrofit: Retrofit, mockApi: MockApi, mock: Mock): ApiService =
    if (mock.isMock) mockApi
    else retrofit.create(ApiService::class.java)

class Mock(val isMock: Boolean)