package com.a4nt0n64r.pubgstats.data.repository

import android.util.Log
import com.a4nt0n64r.pubgstats.domain.model.*
import com.a4nt0n64r.pubgstats.network.ApiService
import com.a4nt0n64r.pubgstats.network.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NetworkRepoImpl(private val apiService: ApiService) : NetworkRepository {

    override fun getNetPlayer(name: String, callback: (PlayerDataFromApi) -> Unit) {
        val call = apiService.getPlayer(name)

        call.enqueue(object : Callback<PlayerDataFromApi> {

            override fun onFailure(call: Call<PlayerDataFromApi>, t: Throwable) {
                Log.d("FAIL", "FAIL что-то не так, игрок не получен!")

            }

            override fun onResponse(call: Call<PlayerDataFromApi>, response: Response<PlayerDataFromApi>) {

                //TODO( вот тут мы можем получить: {"errors":[{"title":"Not Found","detail":"No Players Found Matching Criteria"}]})
                // Это надо обработать как "ИГРОК НЕ НАЙДЕН"
                val data = response.body()
                if (data != null) callback.invoke(data)
            }
        })
    }

    override fun getNetSeasons(callback: (SeasonsDataFromApi) -> Unit) {
        val call = apiService.getSeasons()

        call.enqueue(object : Callback<SeasonsDataFromApi> {


            override fun onFailure(call: Call<SeasonsDataFromApi>, t: Throwable) {
                Log.d("FAIL", "FAIL что-то не так!")
            }


            override fun onResponse(call: Call<SeasonsDataFromApi>, response: Response<SeasonsDataFromApi>) {
                Log.d("OK", "Сезоны получены из Интернета!")
                val data = response.body()
                if (data != null) callback.invoke(data)
            }
        })
    }

    override fun getNetStatistics(
        playerDB: PlayerDB,
        season: SeasonDB,
        callback: (StatisticsFromApi) -> Unit
    ) {
        val call = apiService.getSeasonStats(season.seasonId)

        call.enqueue(object : Callback<StatisticsFromApi> {


            override fun onFailure(call: Call<StatisticsFromApi>, t: Throwable) {
                Log.d("FAIL", "FAIL что-то не так!")
            }


            override fun onResponse(call: Call<StatisticsFromApi>, response: Response<StatisticsFromApi>) {
                Log.d("OK", "Статистика за сезон получена!")
                //здесь нужно запускать функцию, которая отобразит статистику в StatFragment
                val data = response.body()
                if (data != null) {
                    callback.invoke(data)
                }
            }
        })
    }
}