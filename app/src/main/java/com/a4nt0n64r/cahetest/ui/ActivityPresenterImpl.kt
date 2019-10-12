package com.a4nt0n64r.cahetest.ui

import com.a4nt0n64r.cahetest.domain.model.Player
import com.a4nt0n64r.cahetest.domain.repository.Repository
import com.a4nt0n64r.cahetest.network.NetworkRepository
import com.a4nt0n64r.cahetest.ui.base.AbstractActivityPresenter
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.*

@InjectViewState
class ActivityPresenterImpl(
) : AbstractActivityPresenter() {

    override fun loadFragment(fragmentId:Int) {
        viewState.changeFragment(fragmentId)
    }

    companion object {
        val TAG = "ActivityPresenterImpl"
    }
}