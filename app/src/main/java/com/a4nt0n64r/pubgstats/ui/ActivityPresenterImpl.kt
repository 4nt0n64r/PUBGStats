package com.a4nt0n64r.pubgstats.ui

import com.a4nt0n64r.pubgstats.ui.base.AbstractActivityPresenter
import moxy.InjectViewState


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