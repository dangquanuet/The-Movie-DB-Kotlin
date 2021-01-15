package com.example.moviedb

import android.app.Application
import android.content.Intent
import android.os.Looper
import android.widget.Toast
import androidx.multidex.MultiDex
import com.example.moviedb.ui.screen.main.MainActivity
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import kotlin.system.exitProcess

@HiltAndroidApp
class MainApplication : Application() {

    // use this to run coroutines that need a longer lifetime than the calling scope (like viewModelScope) might offer in our app
    val appScope by lazy {
        CoroutineScope(SupervisorJob())
    }

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)

        if (enableLogging()) {
            // init timber
            Timber.plant(Timber.DebugTree())

            // init stetho
            Stetho.initializeWithDefaults(this)
        } else {
            handleUncaughtException()
        }
    }

    /**
     * prevent uncaught exception to crash app
     * restart app for better UX
     */
    private fun handleUncaughtException() {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            object : Thread() {
                override fun run() {
                    Looper.prepare()
                    Toast.makeText(applicationContext, R.string.unknown_error, Toast.LENGTH_SHORT)
                        .show()
                    Looper.loop()
                }
            }.start()

            Thread.sleep(2000)

            val intent = Intent(this, MainActivity::class.java)
            // to custom behaviour, add extra params for intent
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
            try {
                exitProcess(2);
            } catch (e: Exception) {
                startActivity(intent)
            }
        }
    }
}

fun enableLogging() = BuildConfig.BUILD_TYPE != "release"
