package com.a4nt0n64r.pubgstats.data.source.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import com.a4nt0n64r.pubgstats.domain.model.StatisticsDB
import java.time.LocalDate

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlayerToDB(player: PlayerDB)

    @Query("SELECT * FROM players_table")
    fun getPlayersFromDB(): List<PlayerDB>

    @Query("DELETE FROM players_table WHERE name_field = :playerName AND id_field = :playerId")
    fun deletePlayer(playerName: String, playerId: String)

    //seasons

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSeasonToDB(season: SeasonDB)

    @Query("DELETE FROM seasons_table")
    fun deleteSeasonsFromDB()

    @Query("SELECT * FROM seasons_table")
    fun getSeasonsFromDB(): List<SeasonDB>

    @Query("SELECT dateOfLastDownload_field FROM seasons_table WHERE type_field = 1")
    fun getLastDownloadDateForSeasons(): LocalDate


    //statistics

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStatisticsToDB(statisticsDB: StatisticsDB)

    @Query("DELETE FROM statistics_table WHERE id_field =:playerId")
    fun deleteStatisticsFromDB(playerId: String)

    @Query("SELECT * FROM statistics_table WHERE id_field =:playerId")
    fun getPlayerStatisticsFromDB(playerId: String): StatisticsDB

    @Query("SELECT lastDownloadStatisticsDate_field FROM statistics_table WHERE id_field =:playerId")
    fun getLastDownloadDateForStatistics(playerId: String): LocalDate

    @Query("SELECT region_field FROM statistics_table WHERE id_field =:playerId")
    fun getRegionForPlayerFromStatistics(playerId: String):String

}
