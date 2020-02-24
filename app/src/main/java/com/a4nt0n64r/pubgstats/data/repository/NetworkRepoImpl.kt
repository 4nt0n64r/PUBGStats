package com.a4nt0n64r.pubgstats.data.repository

import android.util.Log
import com.a4nt0n64r.pubgstats.domain.model.*
import com.a4nt0n64r.pubgstats.network.ApiService
import com.a4nt0n64r.pubgstats.network.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
