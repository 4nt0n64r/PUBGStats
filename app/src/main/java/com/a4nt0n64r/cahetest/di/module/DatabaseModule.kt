package com.a4nt0n64r.cahetest.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.a4nt0n64r.cahetest.data.repository.MainRepoImpl
import com.a4nt0n64r.cahetest.data.source.db.MyDataBase
import com.a4nt0n64r.cahetest.data.source.db.PlayerDao
import com.a4nt0n64r.cahetest.domain.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): MyDataBase {
        return Room
            .databaseBuilder(application, MyDataBase::class.java, MyDataBase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(appDataBase: MyDataBase): PlayerDao = appDataBase.playerDAO()


    @Provides
    @Singleton
    fun provideRepository(database: MyDataBase): Repository = MainRepoImpl(database)
}