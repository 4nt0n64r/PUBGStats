package com.a4nt0n64r.pubgstats.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "players_table")
data class PlayerDB(
    @ColumnInfo(name = "name_field") val name: String,
    @PrimaryKey
    @ColumnInfo(name = "id_field") val id: String,
    @ColumnInfo(name = "region") val region: String,
    @ColumnInfo(name = "platform") val platform: String
//    @ColumnInfo(name = "timestamp") var timestamp: Timestamp = Timestamp(0)
)

@Entity(tableName = "seasons_table")
data class SeasonDB(
    @PrimaryKey
    @ColumnInfo(name = "id_field")
    val seasonId: String,
    @ColumnInfo(name = "name_field")
    val name: String,
    @ColumnInfo(name = "type_field")
    val isCurrentSeason:Boolean
)

@Entity(tableName = "download_table")
data class SeasonsDownloadDate(
    @PrimaryKey
    @ColumnInfo(name = "date_field") val lastDownloadOfSeasons: Int
)

data class PlayerUI(
    val name: String,
    val id: String,
    val region: String,
    val platform: String,
    var isSelected: Boolean
)
