package com.example.moviedb.data.flow

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulerProvider : SchedulerProvider {

    override fun io() = Schedulers.io()

    override fun ui() = AndroidSchedulers.mainThread()

}