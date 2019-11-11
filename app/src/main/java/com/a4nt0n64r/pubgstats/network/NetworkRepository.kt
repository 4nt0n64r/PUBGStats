package com.a4nt0n64r.pubgstats.network

import com.a4nt0n64r.pubgstats.domain.model.*


interface NetworkRepository {

    fun getNetPlayer(name: String, callback: (PlayerDataFromApi) -> Unit)
    suspend fun getNetSeasons(/*platform?? (default = steam)*/): SeasonsDataFromApi?
    fun getNetStatistics(playerDB: PlayerDB, season:SeasonDB,callback: (StatisticsFromApi) -> Unit)

}
