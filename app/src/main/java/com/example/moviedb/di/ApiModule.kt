package com.example.moviedb.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.example.moviedb.BuildConfig
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.MockApi
import com.example.moviedb.data.remote.RxErrorHandlingCallAdapterFactory
import com.example.moviedb.di.Properties.GOOGLE_MAP_APIS_BASE_URL
import com.example.moviedb.di.Properties.TIME_OUT
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpCache(get()) }
    single(name = "logging") { createLoggingInterceptor() }
    single(name = "header") { createHeaderInterceptor() }
    single { createOkHttpClient(get(), get(name = "logging"), get(name = "header")) }
    single { createAppRetrofit(get()) }
    single { createApiService(get()) }
}

object Properties {
    const val TIME_OUT = 10
    const val GOOGLE_MAP_APIS_BASE_URL = "https://maps.googleapis.com"
}

fun createOkHttpCache(context: Context): Cache {
    val size = (10 * 1024 * 1024).toLong() // 10 Mb
    return Cache(context.cacheDir, size)
}

fun createLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
    return logging
}

fun createHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url().newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMBD_API_KEY)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .header("Content-Type", "application/json")
//                    .header("X-App-Secret", "EZ1hEQ2NOUpT-tBUgw2ADQ")
//                    .header("Authorization", UserDataManager.getAccessToken())
            .method(request.method(), request.body())
            .build()
        chain.proceed(newRequest)
    }
}

fun createOkHttpClient(
    cache: Cache,
    logging: Interceptor,
    header: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .cache(cache)
        .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        .addInterceptor(header)
        .addInterceptor(logging)
        .build()
}

fun createAppRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()
}

fun createMapRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(GOOGLE_MAP_APIS_BASE_URL)
        .client(okHttpClient)
        .build()
}

fun createApiService(retrofit: Retrofit): ApiService {
    return if (BuildConfig.MOCK_DATA) MockApi()
    else retrofit.create(ApiService::class.java)
}

//    @Provides
//    @Singleton
//    RestService provideMockService(@Named("app_retrofit") Retrofit retrofit) {
//        NetworkBehavior networkBehavior = NetworkBehavior.create();
//        // Reduce the delay to make the next calls complete faster.
//        networkBehavior.setDelay(500, TimeUnit.MILLISECONDS);
//        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit).networkBehavior(networkBehavior).build();
//        BehaviorDelegate<RestService> restServiceBehaviorDelegate = mockRetrofit.create(RestService.class);
//        MockRestService mockRestService = new MockRestService(restServiceBehaviorDelegate);
//        return mockRestService;
//    }

//    @Provides
//    @Singleton
//    internal fun provideMapService(@Named("map_retrofit") retrofit: Retrofit): GoogleMapsApi {
//        return retrofit.create(GoogleMapsApi::class.java!!)
//    }
