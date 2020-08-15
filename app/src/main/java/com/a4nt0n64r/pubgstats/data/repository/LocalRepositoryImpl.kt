package com.a4nt0n64r.pubgstats.data.repository

import com.a4nt0n64r.pubgstats.data.source.db.Dao
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import com.a4nt0n64r.pubgstats.domain.model.StatisticsDB
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository
import java.time.LocalDate

class LocalRepositoryImpl(private val dao: Dao) : LocalRepository {

    override suspend fun addPlayerToDB(player: PlayerDB) {
        dao.addPlayerToDB(player)
    }

    override suspend fun getAllPlayersFromDB(): List<PlayerDB> {
        return dao.getPlayersFromDB()
    }

    override suspend fun deletePlayerFromDB(player: PlayerDB) {
        dao.deletePlayer(player.name, player.id)
    }

    override suspend fun addSeasonToDB(seasonDB: SeasonDB) {
        dao.addSeasonToDB(seasonDB)
    }

    override suspend fun getAllSeasonsFromDB(): List<SeasonDB> {
        return dao.getSeasonsFromDB()
    }

    override suspend fun getLastDownloadSeasonsDate(): LocalDate {
        return dao.getLastDownloadDateForSeasons()
    }

    override suspend fun deleteSeasonsFromDB() {
        dao.deleteSeasonsFromDB()
    }

    override suspend fun addStatisticsForPlayer(statisticsDB: StatisticsDB) {
        dao.addStatisticsToDB(statisticsDB)
    }

    override suspend fun getStatisticsForPlayer(player: PlayerDB): StatisticsDB {
        return dao.getPlayerStatisticsFromDB(player.id)
    }

    override suspend fun getLastDownloadStatisticsDate(playerId:String): LocalDate {
        return dao.getLastDownloadDateForStatistics(playerId)
    }

    override suspend fun deleteStatisticsForPlayer(player: PlayerDB) {
        dao.deleteStatisticsFromDB(player.id)
    }

    override suspend fun getRegionForPlayerStatistics(playerId: String): String {
        return dao.getRegionForPlayerFromStatistics(playerId)
    }
}
