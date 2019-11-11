package com.a4nt0n64r.pubgstats.core

import android.app.Application
import com.a4nt0n64r.pubgstats.di.modules.applicationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class PUBGStatsApplication :  Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin { androidContext(this@PUBGStatsApplication)
        modules(applicationModules)}
    }


}
