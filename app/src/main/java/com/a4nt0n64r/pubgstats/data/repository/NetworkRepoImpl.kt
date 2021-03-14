package com.a4nt0n64r.pubgstats.data.repository

import com.a4nt0n64r.pubgstats.domain.model.SeasonsDataFromApi
import com.a4nt0n64r.pubgstats.domain.model.PlayerDataFromApi
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import com.a4nt0n64r.pubgstats.domain.model.StatisticsFromApi
import com.a4nt0n64r.pubgstats.network.ApiService
import com.a4nt0n64r.pubgstats.network.NetworkRepository
import java.io.IOException


class NetworkRepoImpl(private val apiService: ApiService) : NetworkRepository {

    @Throws(IOException::class, RuntimeException::class)
    override suspend fun getNetPlayer(name: String): PlayerDataFromApi {
        val call = apiService.getPlayer(name)
        return call.execute().body()!!
    }

    @Throws(IOException::class, RuntimeException::class)
    override suspend fun getNetSeasons(): SeasonsDataFromApi? {
        val call = apiService.getSeasons()
        return call.execute().body()
    }

    @Throws(IOException::class, RuntimeException::class)
    override suspend fun getNetStatistics(playerDB: PlayerDB, season: SeasonDB): StatisticsFromApi {
        val call = apiService.getSeasonStats(playerDB.id,season.seasonId)
        return call.execute().body()!!
    }
}
