package com.example.moviedb.di

import com.example.moviedb.data.flow.AppSchedulerProvider
import com.example.moviedb.data.flow.SchedulerProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import org.koin.experimental.builder.create

val appModule = module {
    single { androidApplication().resources }
    single<SchedulerProvider> { create<AppSchedulerProvider>() }
}

val modules = listOf(
    appModule,
    networkModule,
    repositoryModule,
    viewModelModule
)

/* naming a definition
val myModule = module {
    single<Service>("default") { ServiceImpl() }
    single<Service>("test") { ServiceImpl() }

    // Will match types ServiceImp & Service
    single { ServiceImp() } bind Service::class
}

val service : Service by inject(name = "default")
 */