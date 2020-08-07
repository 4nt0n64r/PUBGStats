package com.a4nt0n64r.pubgstats.network

import com.a4nt0n64r.pubgstats.domain.model.*
import retrofit2.HttpException


interface NetworkRepository {

    suspend fun getNetPlayer(name: String):PlayerDataFromApi
    suspend fun getNetSeasons(): SeasonsDataFromApi?
    suspend fun getNetStatistics(playerDB: PlayerDB, season:SeasonDB):StatisticsFromApi

}
