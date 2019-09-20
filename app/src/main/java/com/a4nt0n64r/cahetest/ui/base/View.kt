package com.a4nt0n64r.cahetest.ui.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

//функции вьюх. Всё что написано тут - то и может нарисовать вьюха, больше ничего.
@StateStrategyType(AddToEndSingleStrategy::class)
interface View :MvpView{
    fun showSnackbar(msg: String)
    fun fillName(name: String)
    fun fillData(data: String)
}