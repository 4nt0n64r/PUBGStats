package com.a4nt0n64r.cahetest.ui.base

import android.widget.TextView
import com.a4nt0n64r.cahetest.domain.repository.Repository
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

//Тут описаны события которые могут происходить (нажата кнопка, выделен элемент...)
interface Presenter{

    fun onDeleteButtonWasClicked(name: String)
    fun onFindButtonWasClicked(name: String)
    fun onShowButtonWasClicked()
    fun onSaveButtonWasClicked(name:String,data:String)

    fun onNetButtonWasClicked()

    fun onDestroy()
}

abstract class AbstractPresenter:MvpPresenter<View>(),Presenter