package com.a4nt0n64r.cahetest.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.a4nt0n64r.cahetest.data.repository.RepoImpl
import com.a4nt0n64r.cahetest.data.source.db.MyDatabase
import com.a4nt0n64r.cahetest.data.source.db.PlayerDao
import com.a4nt0n64r.cahetest.domain.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): MyDatabase {
        return Room
            .databaseBuilder(application, MyDatabase::class.java, MyDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: MyDatabase): PlayerDao = appDatabase.playerDao()
}