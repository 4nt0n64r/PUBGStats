package com.a4nt0n64r.pubgstats.ui.base

import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.PlayerUI
import com.a4nt0n64r.pubgstats.domain.model.StatisticsItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


//функции вьюх. Всё что написано тут - то и может нарисовать вьюха, больше ничего.
@StateStrategyType(SingleStateStrategy::class)
interface ActivityView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun drawAddPlayerFragment()

    fun drawListOfPlayersFragment()

    fun showStatisticsFragmet(player: PlayerDB)

    fun requestStoragePermission()
    fun requestInternetPermission()
}

@StateStrategyType(SkipStrategy::class)
interface AddPlayerFragmentView : MvpView {

    fun showSnackbar(messageId: Int)

    fun showLoading()
    fun hideLoading()

    @StateStrategyType(SkipStrategy::class)
    fun changeFragment()

    fun requestInternetPermissionFromFragment()
}

@StateStrategyType(SkipStrategy::class)
interface ListOfPlayersFragmentView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showMinusButton()

    @StateStrategyType(SkipStrategy::class)
    fun hideMinusButton()

    @StateStrategyType(SkipStrategy::class)
    fun showErrorTextAndImage()

    @StateStrategyType(SkipStrategy::class)
    fun hideErrorTextAndImage()

    @StateStrategyType(SkipStrategy::class)
    fun showPlayers(players: List<PlayerUI>)

    @StateStrategyType(SkipStrategy::class)
    fun changeFragment(fragId: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showPlayerStatistics(player: PlayerDB)

    @StateStrategyType(SkipStrategy::class)
    fun showSnackbar(messageId: Int)

    @StateStrategyType(SkipStrategy::class)
    fun selectionModeOff()
}

@StateStrategyType(SingleStateStrategy::class)
interface StatisticsFragmentView : MvpView {

    fun showStatistics(statistics: List<StatisticsItem>)
    fun initiateStatisticsLoading()

    fun showLoading()
    fun hideLoading()

    fun showPlayerName(name: String)
    fun showRegimes(tpp: String, fpp: String)
}
