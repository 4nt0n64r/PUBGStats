package com.a4nt0n64r.pubgstats.ui.fragments.addPlayer

import androidx.annotation.WorkerThread
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository
import com.a4nt0n64r.pubgstats.network.NetworkRepository
import com.a4nt0n64r.pubgstats.ui.base.AbstractAddPlayerPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.Dispatchers
import moxy.InjectViewState
import retrofit2.HttpException

@InjectViewState
class AddPlayerPresenterImpl(
    private val localRepository: LocalRepository,
    private val networkRepository: NetworkRepository
) : AbstractAddPlayerPresenter() {

    private val job: Job by lazy { SupervisorJob() }


    override fun requestPlayer(
        name: String?,
        region: String,
        platform: String,
        isConnected: Boolean
    ) {
        if (isConnected) {
            if (name == "") {
                viewState.showSnackbar(EMPTY_NAME)
            } else {
                getPlayerFromApi(name!!, region, platform)
            }
        } else {
            viewState.showSnackbar(NO_INTERNET)
        }
    }

    @Suppress("TooGenericExceptionCaught") // Непонятно пока как отлавливать
    @WorkerThread
    private fun getPlayerFromApi(name: String, region: String, platform: String) {
        viewState.showLoading()
        CoroutineScope(Dispatchers.IO + job).launch {
            try {
                viewState.requestInternetPermissionFromFragment()
                val dataFromApi = networkRepository.getNetPlayer(name)
                localRepository.addPlayerToDB(
                    PlayerDB(
                        dataFromApi.getName(),
                        dataFromApi.getId(),
                        region,
                        platform
                    )
                )
                viewState.hideLoading()
                viewState.changeFragment()
            } catch (e: NullPointerException) {
                viewState.hideLoading()
                viewState.showSnackbar(NOT_FOUND)
            } catch (e: HttpException) {
                viewState.hideLoading()
                viewState.showSnackbar(NO_INTERNET)
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
    }
}
