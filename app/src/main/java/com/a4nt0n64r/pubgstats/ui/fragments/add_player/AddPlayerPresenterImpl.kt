package com.a4nt0n64r.pubgstats.ui.fragments.add_player

import android.Manifest
import androidx.annotation.WorkerThread
import androidx.core.app.ActivityCompat
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository
import com.a4nt0n64r.pubgstats.network.NetworkRepository
import com.a4nt0n64r.pubgstats.ui.base.AbstractAddPlayerPresenter
import kotlinx.coroutines.*
import moxy.InjectViewState

@InjectViewState
class AddPlayerPresenterImpl(
    private val localRepository: LocalRepository,
    private val networkRepository: NetworkRepository
) : AbstractAddPlayerPresenter() {

    private val job: Job by lazy { SupervisorJob() }

    override fun requestPlayer(name: String?) {
        if (name == "") {
            viewState.showSnackbar("Введите имя!")
        } else {
            getPlayerFromApi(name!!)
            viewState.changeFragment()
        }
    }

    @WorkerThread
    private fun getPlayerFromApi(name: String) {
        CoroutineScope(Dispatchers.IO + job).launch {
            try {
                viewState.requestInternetPermissionFromFragment()
                val dataFromApi = networkRepository.getNetPlayer(name)
                localRepository.addPlayerToDB(PlayerDB(dataFromApi.getName(), dataFromApi.getId()))
            } catch (e: NullPointerException) {
                viewState.showSnackbar("Данные не пришли!")
            }

        }
    }

    override fun onDestroy() {
        job.cancel()
    }

}
