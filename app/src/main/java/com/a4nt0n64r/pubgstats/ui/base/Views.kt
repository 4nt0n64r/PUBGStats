package com.a4nt0n64r.pubgstats.ui.base

import com.a4nt0n64r.pubgstats.domain.model.*
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


//функции вьюх. Всё что написано тут - то и может нарисовать вьюха, больше ничего.
@StateStrategyType(SingleStateStrategy::class)
interface ActivityView : MvpView {

    fun drawFragment(fragmentId: Int)
    fun showStatisticsFragmet(player: PlayerDB)

    fun requestStoragePermission()
    fun requestInternetPermission()
}

@StateStrategyType(SingleStateStrategy::class)
interface AddPlayerFragmentView : MvpView {

    fun showSnackbar(msg: String)

    @StateStrategyType(SkipStrategy::class)
    fun changeFragment()

    fun requestInternetPermissionFromFragment()

}

@StateStrategyType(SingleStateStrategy::class)
interface ListOfPlayersFragmentView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showMinusButton()
    fun hideMinusButton()

    fun showPlayers(players: List<PlayerDBUI>)

    @StateStrategyType(SkipStrategy::class)
    fun changeFragment(fragId: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showPlayerStatistics(player: PlayerDB)

}

@StateStrategyType(SingleStateStrategy::class)
interface StatisticsFragmentView : MvpView {

    fun showStatistics(statistics: List<StatisticsItem>)
    fun showSeasons(seasons:List<SeasonDB>)
    fun showPlayerName(player: PlayerDB)


}