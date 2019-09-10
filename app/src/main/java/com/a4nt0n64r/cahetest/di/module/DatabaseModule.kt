package com.a4nt0n64r.cahetest.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.a4nt0n64r.cahetest.data.source.db.MyDataBase
import com.a4nt0n64r.cahetest.data.source.db.PlayerDao
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
    fun provideUserDao(appDataBase: MyDataBase): PlayerDao {
        return appDataBase.playerDAO()
    }
}