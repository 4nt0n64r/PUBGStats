package com.a4nt0n64r.cahetest.ui.base

//функции вьюх. Всё что написано тут - то и может нарисовать вьюха, больше ничего.
interface View {
    fun showSnackbar(msg: String)
    fun fillName(name: String)
    fun fillData(data: String)
}