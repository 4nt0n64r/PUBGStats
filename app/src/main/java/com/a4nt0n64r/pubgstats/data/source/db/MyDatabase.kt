package com.a4nt0n64r.pubgstats.data.source.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.a4nt0n64r.pubgstats.domain.model.SeasonsDownloadDate
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB

@Database(
    entities = [PlayerDB::class, SeasonDB::class, SeasonsDownloadDate::class],
    version = MyDatabase.VERSION,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "databse.db"
        const val VERSION = 1
    }

    abstract fun playerDao(): Dao

    fun createDatabase(context: Context) {
        Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, MyDatabase.DB_NAME)
            .build()
    }
}

