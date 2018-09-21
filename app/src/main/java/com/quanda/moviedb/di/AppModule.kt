package com.quanda.moviedb.di

import android.content.res.Resources
import com.quanda.moviedb.MainApplication
import org.koin.dsl.module.module

val appModule = module(override = true) {
    single { createResources(get()) }
}

fun createResources(application: MainApplication): Resources = application.resources

/* naming a definition
val myModule = module {
    single<Service>("default") { ServiceImpl() }
    single<Service>("test") { ServiceImpl() }

    // Will match types ServiceImp & Service
    single { ServiceImp() } bind Service::class
}

val service : Service by inject(name = "default")
 */