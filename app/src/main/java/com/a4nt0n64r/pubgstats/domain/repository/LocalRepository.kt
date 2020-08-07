package com.a4nt0n64r.pubgstats.domain.repository

import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import com.a4nt0n64r.pubgstats.domain.model.StatisticsDB
import java.time.LocalDate

//Тут описаны все взаимодействия с Model = Бизнес логика = данные
interface LocalRepository {

    suspend fun addPlayerToDB(player: PlayerDB)
    suspend fun getAllPlayersFromDB(): List<PlayerDB>
    suspend fun deletePlayerFromDB(player: PlayerDB)

    suspend fun addSeasonToDB(seasonDB: SeasonDB)
    suspend fun getAllSeasonsFromDB(): List<SeasonDB>
    suspend fun getLastDownloadSeasonsDate(): LocalDate
    suspend fun deleteSeasonsFromDB()

    suspend fun addStatisticsForPlayer(statisticsDB: StatisticsDB)
    suspend fun getStatisticsForPlayer(player: PlayerDB): StatisticsDB
    suspend fun getLastDownloadStatisticsDate(playerId:String): LocalDate
    suspend fun deleteStatisticsForPlayer(player: PlayerDB)
}