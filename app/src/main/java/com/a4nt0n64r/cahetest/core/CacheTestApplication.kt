package com.a4nt0n64r.cahetest.core

import android.app.Application
import com.a4nt0n64r.cahetest.di.component.CoreComponent
import com.a4nt0n64r.cahetest.di.component.DaggerCoreComponent
import com.a4nt0n64r.cahetest.di.module.AppModule


class CacheTestApplication : Application() {

    lateinit var someComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        someComponent = initDagger(this)
    }

    private fun initDagger(app: CacheTestApplication): CoreComponent =
        DaggerCoreComponent.builder()
            .appModule(AppModule(app))
            .build()

}
