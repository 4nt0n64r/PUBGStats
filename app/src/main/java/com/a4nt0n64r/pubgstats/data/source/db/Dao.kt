package com.a4nt0n64r.pubgstats.data.source.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a4nt0n64r.pubgstats.domain.model.SeasonsDownloadDate
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlayerToDB(player: PlayerDB)

    @Query("SELECT * FROM players_table")
    fun getPlayersFromDB():List<PlayerDB>

    @Query("DELETE FROM players_table WHERE name_field = :playerName AND id_field = :playerId")
    fun deletePlayer(playerName: String, playerId:String)

    //seasons

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSeasonToDB(season: SeasonDB)

    @Query("DELETE FROM seasons_table")
    fun deleteSeasonsFromDB()

    @Query("SELECT * FROM seasons_table")
    fun getSeasonsFromDB():List<SeasonDB>

    //date

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDateToDB(dateSeasons: SeasonsDownloadDate)

    @Query("SELECT * from download_table" )
    fun getDate(): SeasonsDownloadDate

    @Query("DELETE FROM download_table" )
    fun deleteDate()

}