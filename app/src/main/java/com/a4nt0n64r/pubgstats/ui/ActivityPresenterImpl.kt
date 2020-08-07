package com.a4nt0n64r.pubgstats.ui

import com.a4nt0n64r.pubgstats.ui.base.AbstractActivityPresenter
import moxy.InjectViewState


@InjectViewState
class ActivityPresenterImpl(
) : AbstractActivityPresenter() {

    override fun loadAddPlayerFragment() {
        viewState.drawAddPlayerFragment()
    }

    override fun loadListOfPlayersFragment() {
        viewState.drawListOfPlayersFragment()
    }

}