package com.a4nt0n64r.pubgstats.core

import android.app.Application
import android.content.res.Resources
import com.a4nt0n64r.pubgstats.di.modules.applicationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class PUBGStatsApplication :  Application(){

    override fun onCreate() {
        super.onCreate()

        instance = this
        res = resources

        val context = this
        startKoin { androidContext(this@PUBGStatsApplication)
        modules(applicationModules)}
    }

    companion object {
        var instance: PUBGStatsApplication? = null
            private set
        private var res: Resources? = null

        val resourses: Resources?
            get() = res
    }
    // тестирую странное решение из интернета
}

