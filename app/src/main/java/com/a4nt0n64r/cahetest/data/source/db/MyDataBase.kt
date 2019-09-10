package com.a4nt0n64r.cahetest.data.source.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.a4nt0n64r.cahetest.domain.model.Player

@Database(entities = [Player::class],
    version = MyDataBase.VERSION,
    exportSchema = false
)
abstract class MyDataBase : RoomDatabase() {

    companion object {
        const val DB_NAME = "players.db"
        const val VERSION = 1
    }

    abstract fun playerDAO(): PlayerDao

    fun createDatabase(context: Context) {
        Room.databaseBuilder(context.applicationContext, MyDataBase::class.java, MyDataBase.DB_NAME)
            .build()
    }
}

