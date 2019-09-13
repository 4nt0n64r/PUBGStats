package com.a4nt0n64r.cahetest.domain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "players")
data class Player(
    @ColumnInfo(name = "name_field") val name: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "data_field") val data: String

)


