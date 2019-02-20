package com.example.moviedb.data.flow

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler

    fun io(): Scheduler
}