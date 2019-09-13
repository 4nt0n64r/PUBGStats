package com.a4nt0n64r.cahetest.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.a4nt0n64r.cahetest.data.source.db.MyDatabase
import com.a4nt0n64r.cahetest.data.source.db.PlayerDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): MyDatabase {
        return Room
            .databaseBuilder(context, MyDatabase::class.java, MyDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: MyDatabase): PlayerDao = appDatabase.playerDao()
}