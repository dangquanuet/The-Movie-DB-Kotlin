package com.example.moviedb.di

import android.content.Context
import android.content.res.AssetManager
import com.example.moviedb.BuildConfig
import com.example.moviedb.data.remote.ApiService
import com.example.moviedb.data.remote.MockApi
import com.example.moviedb.data.remote.MockInterceptor
import com.example.moviedb.enableLogging
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    val TIMEOUT = 10

    @Singleton
    @Provides
    fun provideOkHttpCache(context: Context): Cache =
        Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

    @Singleton
    @Provides
    @Named("logging")
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (enableLogging()) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    @Named("header")
    fun provideHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newUrl = request.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMBD_API_KEY)
                .build()
            val newRequest = request.newBuilder()
                .url(newUrl)
                .method(request.method, request.body)
                .build()
            chain.proceed(newRequest)
        }

    @Singleton
    @Provides
    @Named("mock")
    fun provideMockInterceptor(assetManager: AssetManager): MockInterceptor =
        MockInterceptor(assetManager)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named("logging") logging: Interceptor,
        @Named("header") header: Interceptor,
        @Named("mock") mockInterceptor: MockInterceptor,
        mock: Mock
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(header)
            .addInterceptor(logging)
            .addNetworkInterceptor(StethoInterceptor())
            .apply {
                if (BuildConfig.DEBUG && mock.isMock) addInterceptor(mockInterceptor)
            }
            .build()

    @Singleton
    @Provides
    fun provideAppRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit, mockApi: MockApi, mock: Mock): ApiService =
        if (mock.isMock) mockApi
        else retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMock(): Mock = Mock(BuildConfig.MOCK_DATA)

    @Singleton
    @Provides
    fun provideMockApi(): MockApi = MockApi()

    class Mock(val isMock: Boolean)
}
