package com.example.moviedb.data.scheduler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class AppSchedulerProvider : SchedulerProvider {

    override val uiContext = SupervisorJob() + Dispatchers.Main

    override val ioContext = SupervisorJob() + Dispatchers.IO

    override val uiScope = CoroutineScope(uiContext)

    override val ioScope = CoroutineScope(ioContext)

}