package com.sonssetiana.networkboundresouce

import android.app.Application
import com.sonssetiana.networkboundresouce.di.domainModules
import com.sonssetiana.networkboundresouce.di.localModules
import com.sonssetiana.networkboundresouce.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.core.context.startKoin

class MyApps: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApps)
            modules(
                listOf(
                    remoteModule,
                    localModules,
                    domainModules
                )
            )
        }
    }
}