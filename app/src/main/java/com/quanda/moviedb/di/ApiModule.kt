package com.quanda.moviedb.di

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.quanda.moviedb.BuildConfig
import com.quanda.moviedb.data.remote.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    companion object {
        const val TIME_OUT = 30
        const val GOOGLE_MAP_APIS_BASE_URL = "https://maps.googleapis.com"
    }

    @Provides
    @Singleton
    @Named("cache")
    internal fun provideOkHttpCache(application: Application): Cache {
        val size = (10 * 1024 * 1024).toLong() // 10 Mb
        return Cache(application.cacheDir, size)
    }

    @Provides
    @Singleton
    @Named("logging")
    internal fun provideLoggingInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    @Singleton
    @Named("header")
    internal fun provideHeaderInterceptor(): Interceptor {
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

    @Provides
    @Singleton
    @Named("okHttp_client")
    internal fun provideOkHttpClient(@Named("cache") cache: Cache,
            @Named("logging") logging: Interceptor,
            @Named("header") header: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(header)
                .addInterceptor(logging)
                .build()
    }

    @Provides
    @Singleton
    @Named("app_retrofit")
    internal fun provideAppRetrofit(@Named("okHttp_client") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    @Named("map_retrofit")
    internal fun provideMapRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GOOGLE_MAP_APIS_BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideApiService(@Named("app_retrofit") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
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
}
