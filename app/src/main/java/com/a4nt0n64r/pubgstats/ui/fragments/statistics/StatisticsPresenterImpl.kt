package com.a4nt0n64r.pubgstats.ui.fragments.statistics

import android.util.Log
import androidx.annotation.WorkerThread
import com.a4nt0n64r.pubgstats.R
import com.a4nt0n64r.pubgstats.core.PUBGStatsApplication
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import com.a4nt0n64r.pubgstats.domain.model.StatisticsDB
import com.a4nt0n64r.pubgstats.domain.model.StatisticsItem
import com.a4nt0n64r.pubgstats.domain.model.StatData
import com.a4nt0n64r.pubgstats.domain.model.StatisticsHeader
import com.a4nt0n64r.pubgstats.domain.model.StatisticsPoints
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository
import com.a4nt0n64r.pubgstats.network.NetworkRepository
import com.a4nt0n64r.pubgstats.ui.base.AbstractStatisticsPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import java.time.LocalDate
import java.time.temporal.ChronoUnit


@InjectViewState
class StatisticsPresenterImpl(
    private val localRepository: LocalRepository,
    private val networkRepository: NetworkRepository
) : AbstractStatisticsPresenter() {

    private lateinit var player: PlayerDB
    private lateinit var seasons: List<SeasonDB>
    private lateinit var statistics: StatisticsDB

    private var previousSeasonName: String = ""
    private var currentSeasonName: String = ""

    private var tpp: String = ""
    private var fpp: String = ""

    private val job: Job by lazy { SupervisorJob() }

    override fun setPlayerParameters(player: PlayerDB) {
        this.player = player
    }

    override fun setSeasonParameters(prevSeason: String, currentSeason: String) {
        this.previousSeasonName = prevSeason
        this.currentSeasonName = currentSeason
    }

    override fun setRegimeParameters(tpp: String, fpp: String) {
        this.tpp = tpp
        this.fpp = fpp
        viewState.showRegimes(this.tpp, this.fpp)
    }

    override fun setSeasons() {
        CoroutineScope(Dispatchers.Main + job).launch {
            val shouldDownloadNewSeasons = withContext(Dispatchers.IO) {
                shouldDownloadNewSeasons()
            }

            viewState.showLoading()

            seasons = withContext(Dispatchers.IO) {
                if (shouldDownloadNewSeasons) {
                    localRepository.deleteSeasonsFromDB()
                    getNewSeasons()
                } else {
                    localRepository.getAllSeasonsFromDB()
                }
            }

            viewState.hideLoading()

            viewState.initiateStatisticsLoading()
        }
    }

    @Suppress("TooGenericExceptionCaught")
    @WorkerThread
    private suspend fun getNewSeasons(): List<SeasonDB> {
        try {

            val dataFromApi = networkRepository.getNetSeasons()
            val previousSeasonApi =
                dataFromApi!!.seasons[dataFromApi.seasons.size - 2]
            val currentSeasonApi = dataFromApi.seasons[dataFromApi.seasons.size - 1]

            val previousSeasonDB =
                SeasonDB(previousSeasonApi.id, previousSeasonName, false, LocalDate.now())
            val currentSeasonDB =
                SeasonDB(currentSeasonApi.id, currentSeasonName, true, LocalDate.now())

            localRepository.addSeasonToDB(currentSeasonDB)
            localRepository.addSeasonToDB(previousSeasonDB)

            return localRepository.getAllSeasonsFromDB()

        } catch (e: NullPointerException) {
            Log.d("ERROR", "No data (seasons)")
            return emptyList()
        }
    }

    override suspend fun shouldDownloadNewSeasons(): Boolean {

        val lastDownloadDate: LocalDate? = localRepository.getLastDownloadSeasonsDate()

        val todayDate = LocalDate.now()

        if (lastDownloadDate != null) {
            Log.d(
                "INFO",
                "For seasons updating left ${DAYS_BEFORE_UPDATE - 
                        ChronoUnit.DAYS.between(lastDownloadDate, todayDate)} days"
            )
            return ChronoUnit.DAYS.between(lastDownloadDate, todayDate) >= DAYS_BEFORE_UPDATE
        } else return true
    }

    override fun setStatistics() {
        CoroutineScope(Dispatchers.IO + job).launch {
            val shouldDownloadStatistics = withContext(Dispatchers.IO) {
                shouldDownloadStatistics()
            }
            withContext(Dispatchers.Main) {
                viewState.showLoading()
            }

            statistics = withContext(Dispatchers.IO) {
                if (shouldDownloadStatistics) {
                    localRepository.deleteStatisticsForPlayer(player)
                    getNewStatistics()
                } else {
                    localRepository.getStatisticsForPlayer(player)
                }
            }
            withContext(Dispatchers.Main) {
                viewState.hideLoading()
            }
            withContext(Dispatchers.Main) {
                viewState.showStatistics(statisticsToListConverter(statistics.soloTpp))
            }
        }
    }


    @WorkerThread
    private suspend fun getNewStatistics(): StatisticsDB {

        val statisticsFromApi = networkRepository.getNetStatistics(player, seasons[0])

        val statisticsDB = StatisticsDB(
            player.id, player.region,
            statisticsFromApi.data.seasonAttributes.gameModeStats.soloFpp,
            statisticsFromApi.data.seasonAttributes.gameModeStats.duoFpp,
            statisticsFromApi.data.seasonAttributes.gameModeStats.squadFpp,
            statisticsFromApi.data.seasonAttributes.gameModeStats.solo,
            statisticsFromApi.data.seasonAttributes.gameModeStats.duo,
            statisticsFromApi.data.seasonAttributes.gameModeStats.squad,
            LocalDate.now()
        )
        return statisticsDB
    }

    override suspend fun shouldDownloadStatistics(): Boolean {
        val lastDownload: LocalDate? = localRepository.getLastDownloadStatisticsDate(player.id)
        val statRegion = localRepository.getRegionForPlayerStatistics(player.id)
        if (statRegion == player.region){
            return if (lastDownload != null){
                val todayDate = LocalDate.now()
                Log.d(
                    "INFO",
                    "For statistics for ${player.name} updating left " +
                            "${ChronoUnit.DAYS.between(lastDownload, todayDate)}"
                )
                ChronoUnit.DAYS.between(lastDownload, todayDate) >= 1
            }else{
                true
            }
        }
        else{
            return true
        }
    }

    override fun setSoloTpp() {
        viewState.showStatistics(statisticsToListConverter(statistics.soloTpp))
    }

    override fun setSoloFpp() {
        viewState.showStatistics(statisticsToListConverter(statistics.soloFpp))
    }

    override fun setDuoTpp() {
        viewState.showStatistics(statisticsToListConverter(statistics.duoTpp))
    }

    override fun setDuoFpp() {
        viewState.showStatistics(statisticsToListConverter(statistics.duoFpp))
    }

    override fun setSquadTpp() {
        viewState.showStatistics(statisticsToListConverter(statistics.squadTpp))
    }

    override fun setSquadFpp() {
        viewState.showStatistics(statisticsToListConverter(statistics.squadFpp))
    }

    override fun onDestroy() {
        job.cancel()
    }


    fun statisticsToListConverter(statisticsData: StatData): List<StatisticsItem> {
        val statistics = listOf<StatisticsItem>(
            StatisticsHeader(PUBGStatsApplication.resourses!!.getString(R.string.stat_txt_rank)),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.rankPoints),
                statisticsData.rankPoints.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.bestRankPoint),
                statisticsData.bestRankPoint.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.roundsPlayed),
                statisticsData.roundsPlayed.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.assists),
                statisticsData.assists.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.top10s),
                statisticsData.top10s.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.mostSurvivalTime),
                statisticsData.mostSurvivalTime.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.swimDistance),
                statisticsData.swimDistance.toString()
            ),

            StatisticsHeader(PUBGStatsApplication.resourses!!.getString(R.string.stat_txt_kills)),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.kills),
                statisticsData.kills.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.longestKill),
                statisticsData.longestKill.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.roundMostKills),
                statisticsData.roundMostKills.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.headshotKills),
                statisticsData.headshotKills.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.dailyKills),
                statisticsData.dailyKills.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.weeklyKills),
                statisticsData.weeklyKills.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.maxKillStreaks),
                statisticsData.maxKillStreaks.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.roadKills),
                statisticsData.roadKills.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.teamKills),
                statisticsData.teamKills.toString()
            ),

            StatisticsHeader(PUBGStatsApplication.resourses!!.getString(R.string.stat_txt_other)),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.dBNOs),
                statisticsData.dBNOs.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.dailyWins),
                statisticsData.dailyWins.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.boosts),
                statisticsData.boosts.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.damageDealt),
                statisticsData.damageDealt.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.days),
                statisticsData.days.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.heals),
                statisticsData.heals.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.losses),
                statisticsData.losses.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.revives),
                statisticsData.revives.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.rideDistance),
                statisticsData.rideDistance.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.suicides),
                statisticsData.suicides.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.vehicleDestroys),
                statisticsData.vehicleDestroys.toString()
            ),
            StatisticsPoints(
                PUBGStatsApplication.resourses!!.getString(R.string.weaponsAcquired),
                statisticsData.weaponsAcquired.toString()
            )
        )
        return statistics
    }
}

const val DAYS_BEFORE_UPDATE = 30
