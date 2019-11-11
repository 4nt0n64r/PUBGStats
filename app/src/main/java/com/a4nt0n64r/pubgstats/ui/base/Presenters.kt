package com.a4nt0n64r.pubgstats.ui.base

import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.SeasonDB
import kotlinx.coroutines.Deferred
import moxy.MvpPresenter


//Тут описаны события которые могут происходить (нажата кнопка, выделен элемент...)
interface ActivityPresenter {

    fun loadFragment(fragmentId: Int)

    fun onDestroy()
}


interface AddPlayerPresenter {

    fun requestPlayer(name: String?)

    fun onDestroy()

}

interface ListOfPlayersPresenter {

    fun requestPlayersFromDB()
    fun deleteSelectedPlayers()
    fun onPlayerTouched(position: Int)
    fun onPlayerPressed(position: Int)

    fun letsAddNewPlayer()

}

interface StatisticsPresenter {

    fun setParameters(player: PlayerDB,prevSeason:String,currentSeason:String)
    fun setSeasons()
    fun shouldDownloadNewSeasons():Deferred<Boolean>

}

abstract class AbstractActivityPresenter : MvpPresenter<ActivityView>(), ActivityPresenter
abstract class AbstractAddPlayerPresenter : MvpPresenter<AddPlayerFragmentView>(),
    AddPlayerPresenter

abstract class AbstractListOfPlayersPresenter : MvpPresenter<ListOfPlayersFragmentView>(),
    ListOfPlayersPresenter

abstract class AbstractStatisticsPresenter : MvpPresenter<StatisticsFragmentView>(),
    StatisticsPresenter