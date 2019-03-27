package com.example.moviedb.data.scheduler

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface SchedulerProvider {

    val uiContext: CoroutineContext

    val ioContext: CoroutineContext

    val uiScope: CoroutineScope

    val ioScope: CoroutineScope

}