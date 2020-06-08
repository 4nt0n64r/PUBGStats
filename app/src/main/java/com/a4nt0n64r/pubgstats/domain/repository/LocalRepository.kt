package com.a4nt0n64r.pubgstats.domain.repository

import com.a4nt0n64r.pubgstats.domain.model.SeasonsDownloadDate
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB

//Тут описаны все взаимодействия с Model = Бизнес логика = данные
interface LocalRepository {

    suspend fun addPlayerToDB(player: PlayerDB)
    suspend fun getAllPlayersFromDB():List<PlayerDB>
    suspend fun deletePlayerFromDB(player: PlayerDB)

    suspend fun addSeasonToDB(seasonDB: SeasonDB)
    suspend fun getAllSeasonsFromDB():List<SeasonDB>
    suspend fun deleteSeasonsFromDB()

    suspend fun addDownloadDateToDB(dateOfDownload: SeasonsDownloadDate)
    suspend fun getDownloadDateFromDB():SeasonsDownloadDate?
    suspend fun deleteDownloadDateFromDB()

}