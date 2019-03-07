package com.example.moviedb.data.scheduler

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler

    fun io(): Scheduler
}