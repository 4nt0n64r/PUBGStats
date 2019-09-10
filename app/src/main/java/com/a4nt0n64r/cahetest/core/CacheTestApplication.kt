package com.a4nt0n64r.cahetest.core

import android.app.Application
import android.arch.persistence.room.Room
import com.a4nt0n64r.cahetest.data.source.db.MyDataBase
import com.a4nt0n64r.cahetest.di.component.CoreComponent
import com.a4nt0n64r.cahetest.di.component.DaggerCoreComponent
import com.a4nt0n64r.cahetest.di.module.AppModule


class CacheTestApplication : Application() {

    lateinit var someComponent: CoreComponent

    lateinit var database: MyDataBase

    override fun onCreate() {
        super.onCreate()
        someComponent = initDagger(this)
        createDatabase()
    }

    private fun initDagger(app: CacheTestApplication): CoreComponent =
        DaggerCoreComponent.builder()
            .appModule(AppModule(app))
            .build()

    fun createDatabase() {
        database = Room.databaseBuilder(this, MyDataBase::class.java, MyDataBase.DB_NAME).build()
        database.createDatabase(this)
    }
}
