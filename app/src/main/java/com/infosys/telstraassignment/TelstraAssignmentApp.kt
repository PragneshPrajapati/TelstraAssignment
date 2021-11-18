package com.infosys.telstraassignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class
 */
@HiltAndroidApp
class TelstraAssignmentApp : Application() {
    companion object {
        @get:Synchronized
        var appContext: TelstraAssignmentApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
