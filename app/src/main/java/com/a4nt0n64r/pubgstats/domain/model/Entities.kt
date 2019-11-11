package com.a4nt0n64r.pubgstats.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players_table")
data class PlayerDB(
    @ColumnInfo(name = "name_field") val name: String,
    @PrimaryKey
    @ColumnInfo(name = "id_field") val id: String
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


//TODO("добавить у даты поле, к какому игроку она относится (по имени)")
@Entity(tableName = "download_table")
data class SeasonsDownloadDate(
    @PrimaryKey
    @ColumnInfo(name = "date_field") val lastDownloadOfSeasons: Int
)

data class PlayerDBUI(
    val name: String,
    val id: String,
    var isSelected: Boolean
)

@Entity(tableName = "statistics_table")
data class StatisticsDB(
    val type:String,
    val playerId:String,
    val playerName:String,
    val statisticsDownloadDate:Int,
    //???
    val gamemode: Gamemode
)