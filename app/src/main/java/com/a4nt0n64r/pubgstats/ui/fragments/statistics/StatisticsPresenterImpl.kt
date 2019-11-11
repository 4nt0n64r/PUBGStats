package com.a4nt0n64r.pubgstats.ui.fragments.statistics


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

    lateinit var player: PlayerDB

    var previousSeasonName: String = ""
    var currentSeasonName: String = ""

    lateinit var seasons: List<SeasonDB>

    private var job: Job? = null

    override fun setParameters(player: PlayerDB, prevSeason: String, currentSeason: String) {
        this.player = player
        this.previousSeasonName = prevSeason
        this.currentSeasonName = currentSeason
        viewState.showPlayerName(this.player)
    }

    //TODO("Сделать функцию читабельной")
    override fun setSeasons() {
        CoroutineScope(Dispatchers.IO).launch {
            if (shouldDownloadNewSeasons().await()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val seasonsFromCloud = CoroutineScope(Dispatchers.IO).async {
                        cloudRepository.getNetSeasons { dataFromApi ->
                            val previousSeasonApi =
                                dataFromApi.seasons[dataFromApi.seasons.size - 2]
                            val currentSeasonApi = dataFromApi.seasons[dataFromApi.seasons.size - 1]
                            CoroutineScope(Dispatchers.IO).launch {
                                val previousSeasonDB = SeasonDB(
                                    previousSeasonApi.id,
                                    previousSeasonName,
                                    false
                                )
                                val currentSeasonDB = SeasonDB(
                                    currentSeasonApi.id,
                                    currentSeasonName,
                                    true
                                )
                                localRepository.addSeasonToDB(currentSeasonDB)
                                localRepository.addSeasonToDB(previousSeasonDB)
                            }
                        }
                        localRepository.addDownloadDateToDB(SeasonsDownloadDate(LocalDate.now().dayOfYear))
                        return@async localRepository.getAllSeasonsFromDB()
                    }
                    seasons = seasonsFromCloud.await()
                    job = CoroutineScope(Dispatchers.Main).launch {
                        viewState.showSeasons(seasons)
                    }
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val seasonsFromDB =
                        CoroutineScope(Dispatchers.IO).async { localRepository.getAllSeasonsFromDB() }
                    seasons = seasonsFromDB.await()
                    job = CoroutineScope(Dispatchers.Main).launch {
                        viewState.showSeasons(seasons)
                    }
                }

            }
        }
    }


    override fun shouldDownloadNewSeasons(): Deferred<Boolean> =
        CoroutineScope(Dispatchers.IO).async {
            val lastDownload = localRepository.getDownloadDateFromDB()
            if (lastDownload != null) {
                if (LocalDate.now().dayOfYear == lastDownload.lastDownloadOfSeasons) {
                    return@async false
                } else return@async true
            } else return@async true
        }


    override fun onDestroy() {
        if (job != null) {
            job!!.cancel()
        }
    }
}

