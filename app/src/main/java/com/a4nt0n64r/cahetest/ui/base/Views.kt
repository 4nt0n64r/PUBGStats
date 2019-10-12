package com.a4nt0n64r.cahetest.ui.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

//функции вьюх. Всё что написано тут - то и может нарисовать вьюха, больше ничего.
@StateStrategyType(SingleStateStrategy::class)
interface ActivityView : MvpView {
    fun changeFragment(fragmentId:Int)
}

@StateStrategyType(SingleStateStrategy::class)
interface FragmentView : MvpView {
    fun showSnackbar(msg: String)
    fun fillName(name: String)
    fun fillData(data: String)

}