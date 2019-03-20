package com.example.moviedb.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Description: Custom mutable live data that used for single event
 *              such as navigation (for configuration change), show toast..
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    /**
     * Single event for no data input. Make call more clear
     * Example: navigation with no data: SingleLiveEvent<Unit>()
     */
    fun call() {
        value = null
    }
}
