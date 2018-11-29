package com.example.moviedb.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val appModule = module {
    single { androidApplication().resources }
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