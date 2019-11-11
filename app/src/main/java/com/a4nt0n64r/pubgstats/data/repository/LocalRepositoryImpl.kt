package com.a4nt0n64r.pubgstats.data.repository

import android.util.Log
import com.a4nt0n64r.pubgstats.data.source.db.Dao
import com.a4nt0n64r.pubgstats.domain.model.SeasonsDownloadDate
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository

class LocalRepositoryImpl(private val dao: Dao) : LocalRepository {

    override suspend fun addPlayerToDB(player: PlayerDB) {
        dao.addPlayerToDB(player)
    }

    override suspend fun getAllPlayersFromDB(): List<PlayerDB> {
        return dao.getPlayersFromDB()
    }

    override suspend fun deletePlayerFromDB(player: PlayerDB) {
        dao.deletePlayer(player.name,player.id)
    }

    override suspend fun addSeasonToDB(seasonDB: SeasonDB) {
        dao.addSeasonToDB(seasonDB)
    }

    override suspend fun getAllSeasonsFromDB(): List<SeasonDB> {
        return dao.getSeasonsFromDB()
    }

    override suspend fun deleteSeasonsFromDB() {
        dao.deleteSeasonsFromDB()
    }

    override suspend fun addDownloadDateToDB(dateOfDownload: SeasonsDownloadDate) {
        dao.addDateToDB(dateOfDownload)
    }

    override suspend fun getDownloadDateFromDB(): SeasonsDownloadDate {
        return dao.getDate()
    }

    override suspend fun deleteDownloadDateFromDB() {
        dao.deleteDate()
    }
}