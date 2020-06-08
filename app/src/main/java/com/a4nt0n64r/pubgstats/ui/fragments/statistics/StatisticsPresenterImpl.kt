package com.a4nt0n64r.pubgstats.ui.fragments.statistics

import android.util.Log
import androidx.annotation.WorkerThread
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.core.PUBGStatsApplication
import com.a4nt0n64r.pubgstats.domain.model.*
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository
import com.a4nt0n64r.pubgstats.network.NetworkRepository
import com.a4nt0n64r.pubgstats.ui.base.AbstractStatisticsPresenter
import kotlinx.coroutines.*
import moxy.InjectViewState
import java.time.LocalDate


@InjectViewState
class StatisticsPresenterImpl(
    private val localRepository: LocalRepository,
    private val networkRepository: NetworkRepository
) : AbstractStatisticsPresenter() {

    private lateinit var player: PlayerDB
    private lateinit var seasons: List<SeasonDB>
    private var statistics: List<StatisticsItem> = emptyList()

    private var previousSeasonName: String = ""
    private var currentSeasonName: String = ""

    private val job: Job by lazy { SupervisorJob() }

    override fun setParameters(player: PlayerDB, prevSeason: String, currentSeason: String) {
        this.player = player
        this.previousSeasonName = prevSeason
        this.currentSeasonName = currentSeason
        displayParameters(this.player)
//        TODO("Почему-то не отображает текствьюхи, очень странно")
    }

    private fun displayParameters(player: PlayerDB) {
        viewState.showPlatform(player.platform)
        viewState.showRegion(player.region)
//        viewState.showPlayerName(player)
    }

    override fun setSeasons() {
        CoroutineScope(Dispatchers.Main + job).launch {
            val shouldDownloadNewSeasons = withContext(Dispatchers.IO) {
                shouldDownloadNewSeasons()
            }

            seasons = withContext(Dispatchers.IO) {
                if (shouldDownloadNewSeasons) {
                    localRepository.deleteSeasonsFromDB()
                    getNewSeasons()
                } else {
                    localRepository.getAllSeasonsFromDB()
                }
            }
            viewState.showSeasons(seasons)
        }
    }

    @WorkerThread
    private suspend fun getNewSeasons(): List<SeasonDB> {
        try {
            val dataFromApi = networkRepository.getNetSeasons()
            val previousSeasonApi =
                dataFromApi!!.seasons[dataFromApi.seasons.size - 2]
            val currentSeasonApi = dataFromApi.seasons[dataFromApi.seasons.size - 1]

            val previousSeasonDB =
                SeasonDB(previousSeasonApi.id, previousSeasonName, false)
            val currentSeasonDB =
                SeasonDB(currentSeasonApi.id, currentSeasonName, true)

            localRepository.addSeasonToDB(currentSeasonDB)
            localRepository.addSeasonToDB(previousSeasonDB)

            localRepository.addDownloadDateToDB(SeasonsDownloadDate(LocalDate.now().dayOfYear))

            return localRepository.getAllSeasonsFromDB()
        } catch (e: NullPointerException) {
            Log.d("ERROR", "No data (seasons)")
            return emptyList()
        }
    }

    override fun setStatistics() {
        CoroutineScope(Dispatchers.IO + job).launch {
            val shouldDownloadStatistics = withContext(Dispatchers.IO) {
                shouldDownloadStatistics()
            }

            if (shouldDownloadStatistics) {
                statistics = getNewStatistics()
                withContext(Dispatchers.Main) {
                    viewState.showStatistics(statistics)
                }
            }
        }
    }

    override suspend fun shouldDownloadNewSeasons(): Boolean {
        val lastDownload = localRepository.getDownloadDateFromDB()
        if (lastDownload != null) {
            return LocalDate.now().dayOfYear != lastDownload.lastDownloadOfSeasons
        } else {
            return true
        }
    }

    @WorkerThread
    private suspend fun getNewStatistics(): List<StatisticsItem> {
        try {
            //(TODO) - cделать выбор по сезону
//            seasonsListUI[spinner.selectedItemPosition].seasonId
            val dataFromApi = networkRepository.getNetStatistics(player, seasons[0])
            statistics = listOf<StatisticsItem>(
                StatisticsHeader(PUBGStatsApplication.resourses!!.getString(R.string.stat_txt_rank)),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.rankPoints),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.rankPoints.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.bestRankPoint),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.bestRankPoint.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.roundsPlayed),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.roundsPlayed.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.assists),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.assists.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.top10s),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.top10s.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.mostSurvivalTime),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.mostSurvivalTime.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.swimDistance),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.swimDistance.toString()
                ),

                StatisticsHeader(PUBGStatsApplication.resourses!!.getString(R.string.stat_txt_kills)),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.kills),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.kills.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.longestKill),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.longestKill.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.roundMostKills),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.roundMostKills.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.headshotKills),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.headshotKills.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.dailyKills),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.dailyKills.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.weeklyKills),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.weeklyKills.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.maxKillStreaks),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.maxKillStreaks.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.roadKills),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.roadKills.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.teamKills),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.teamKills.toString()
                ),

                StatisticsHeader(PUBGStatsApplication.resourses!!.getString(R.string.stat_txt_other)),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.dBNOs),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.dBNOs.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.dailyWins),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.dailyWins.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.boosts),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.boosts.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.damageDealt),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.damageDealt.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.days),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.days.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.heals),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.heals.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.losses),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.losses.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.revives),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.revives.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.rideDistance),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.rideDistance.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.suicides),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.suicides.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.vehicleDestroys),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.vehicleDestroys.toString()
                ),
                StatisticsPoints(
                    PUBGStatsApplication.resourses!!.getString(R.string.weaponsAcquired),
                    dataFromApi.data.seasonAttributes.gameModeStats.solo_fpp.weaponsAcquired.toString()
                )
            )
            //TODO добавить статистику в DB
//            localRepository.addSeasonToDB(currentSeasonDB)

            //TODO добавить дату в DB
//            localRepository.addDownloadDateToDB(SeasonsDownloadDate(LocalDate.now().dayOfYear))

            //TODO вернуть статистику из DB
//            return localRepository.getAllSeasonsFromDB()
            return statistics
        } catch (e: NullPointerException) {
            Log.d("ERROR", "No data (seasons)")
            val dataFromApi = networkRepository.getNetStatistics(player, seasons[0])
            return emptyList()
        }
    }

    override suspend fun shouldDownloadStatistics(): Boolean {
        //переделать
        return true
    }

    override fun onDestroy() {
        job.cancel()
    }
}

