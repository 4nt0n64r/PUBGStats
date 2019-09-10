package com.a4nt0n64r.cahetest.ui.base

import android.widget.TextView

//Тут описаны события которые могут происходить (нажата кнопка, выделен элемент...)
interface Presenter {

    fun setView(view: View)

    fun onDeleteButtonWasClicked(name: String)
    fun onFindButtonWasClicked(name: String)
    fun onShowButtonWasClicked()
    fun onSaveButtonWasClicked(name:String,data:String)

    fun onDestroy()
}