package com.a4nt0n64r.cahetest.core

import android.app.Application
import com.a4nt0n64r.cahetest.di.modules.applicationModules
import org.koin.android.ext.android.startKoin


class CacheTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(applicationModules))
    }

}
