package com.a4nt0n64r.pubgstats.data.source.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a4nt0n64r.pubgstats.domain.model.*

@Database(
    entities = [PlayerDB::class, SeasonDB::class, StatisticsDB::class],
    version = MyDatabase.VERSION,
    exportSchema = false
)
@TypeConverters(DateConverters::class, StatisticsConverters::class)
abstract class MyDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "database.db"
        const val VERSION = 1
    }

    abstract fun playerDao(): Dao

    fun createDatabase(context: Context) {
        Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, DB_NAME)
            .build()
    }
}

