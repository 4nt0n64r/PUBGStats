package com.a4nt0n64r.pubgstats.network

import com.a4nt0n64r.pubgstats.domain.model.PlayerDataFromApi
import com.a4nt0n64r.pubgstats.domain.model.SeasonsDataFromApi
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import com.a4nt0n64r.pubgstats.domain.model.StatisticsFromApi


interface NetworkRepository {

    suspend fun getNetPlayer(name: String):PlayerDataFromApi
    suspend fun getNetSeasons(): SeasonsDataFromApi?
    suspend fun getNetStatistics(playerDB: PlayerDB, season:SeasonDB):StatisticsFromApi

}
