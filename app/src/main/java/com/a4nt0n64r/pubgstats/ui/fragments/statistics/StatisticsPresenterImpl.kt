package com.a4nt0n64r.pubgstats.ui.fragments.statistics

import androidx.annotation.WorkerThread
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonsDownloadDate
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository
import com.a4nt0n64r.pubgstats.network.NetworkRepository
import com.a4nt0n64r.pubgstats.ui.base.AbstractStatisticsPresenter
import kotlinx.coroutines.*
import moxy.InjectViewState
import java.time.LocalDate

@InjectViewState
class StatisticsPresenterImpl(
    private val localRepository: LocalRepository,
    private val cloudRepository: NetworkRepository
) : AbstractStatisticsPresenter() {

    // старайся не использовать lateinit.
    // засовывай в конструктор лучше или определяй что-то по умолчанию
    private lateinit var seasons: List<SeasonDB>
    private lateinit var player: PlayerDB

    private var previousSeasonName: String = ""
    private var currentSeasonName: String = ""

    private val job: Job by lazy { SupervisorJob() }

    override fun setParameters(player: PlayerDB, prevSeason: String, currentSeason: String) {
        this.player = player
        this.previousSeasonName = prevSeason
        this.currentSeasonName = currentSeason
        viewState.showPlayerName(this.player)
    }

    override fun setSeasons() {
        CoroutineScope(Dispatchers.Main + job).launch {
            val shouldDownloadNewSeasons = withContext(Dispatchers.IO) {
                shouldDownloadNewSeasons()
            }

            seasons = withContext(Dispatchers.IO) {
                if (shouldDownloadNewSeasons) {
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
        val dataFromApi = cloudRepository.getNetSeasons()
            ?: throw NullPointerException("Данные должны прийти!")
        // тут надо try-catch замутить

        val previousSeasonApi =
            dataFromApi.seasons[dataFromApi.seasons.size - 2]
        val currentSeasonApi = dataFromApi.seasons[dataFromApi.seasons.size - 1]

        val previousSeasonDB =
            SeasonDB(previousSeasonApi.id, previousSeasonName, false)
        val currentSeasonDB =
            SeasonDB(currentSeasonApi.id, currentSeasonName, true)

        localRepository.addSeasonToDB(currentSeasonDB)
        localRepository.addSeasonToDB(previousSeasonDB)

        localRepository.addDownloadDateToDB(SeasonsDownloadDate(LocalDate.now().dayOfYear))
        return localRepository.getAllSeasonsFromDB()
    }

    override suspend fun shouldDownloadNewSeasons(): Boolean {
        val lastDownload = localRepository.getDownloadDateFromDB()
        return LocalDate.now().dayOfYear != lastDownload.lastDownloadOfSeasons
    }

    override fun onDestroy() {
        job.cancel()
    }
}

