package com.a4nt0n64r.pubgstats.ui.base

import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import moxy.MvpPresenter

interface ActivityPresenter {

    fun loadAddPlayerFragment()
    fun loadListOfPlayersFragment()
    fun onDestroy()
}

interface AddPlayerPresenter {

    fun requestPlayer(name: String?, region: String, platform: String, isConnected: Boolean)
    fun onDestroy()
}

interface ListOfPlayersPresenter {

    fun requestPlayersFromDB()
    fun deleteSelectedPlayers()
    fun onPlayerTouched(position: Int, isConnected: Boolean)
    fun onPlayerPressed(position: Int)

    fun letsAddNewPlayer()

}

interface StatisticsPresenter {

    fun setPlayerParameters(player: PlayerDB)
    fun setSeasonParameters(prevSeason: String, currentSeason: String)
    fun setRegimeParameters(tpp: String, fpp: String)

    fun setSeasons()
    suspend fun shouldDownloadNewSeasons(): Boolean

    fun setStatistics()
    suspend fun shouldDownloadStatistics(): Boolean

    fun setSoloTpp()
    fun setSoloFpp()
    fun setDuoTpp()
    fun setDuoFpp()
    fun setSquadTpp()
    fun setSquadFpp()

}

abstract class AbstractActivityPresenter : MvpPresenter<ActivityView>(), ActivityPresenter
abstract class AbstractAddPlayerPresenter : MvpPresenter<AddPlayerFragmentView>(),
    AddPlayerPresenter

abstract class AbstractListOfPlayersPresenter : MvpPresenter<ListOfPlayersFragmentView>(),
    ListOfPlayersPresenter

abstract class AbstractStatisticsPresenter : MvpPresenter<StatisticsFragmentView>(),
    StatisticsPresenter
