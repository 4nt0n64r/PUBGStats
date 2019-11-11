package com.a4nt0n64r.pubgstats.ui.fragments.add_player

import android.util.Log
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.PlayerDataFromApi
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository
import com.a4nt0n64r.pubgstats.network.NetworkRepository
import com.a4nt0n64r.pubgstats.ui.LIST_OF_PLAYERS
import com.a4nt0n64r.pubgstats.ui.MainActivity
import com.a4nt0n64r.pubgstats.ui.base.AbstractAddPlayerPresenter
import kotlinx.coroutines.*
import moxy.InjectViewState

@InjectViewState
class AddPlayerPresenterImpl(
    private val localRepository: LocalRepository,
    private val cloudRepository: NetworkRepository
) : AbstractAddPlayerPresenter() {

    private var job: Job? = null

    override fun requestPlayer(name: String?) = if (!name.isNullOrEmpty()) {
        job = CoroutineScope(Dispatchers.IO).launch{
            cloudRepository.getNetPlayer(name) { data ->
                if (data is PlayerDataFromApi) {
                    //Есди игрок не найден:
//                        TODO("{\"errors\":[{\"title\":\"Not Found\",\"detail\":\"No Players Found Matching Criteria\"}]}")
                    CoroutineScope(Dispatchers.IO).launch {
                        localRepository.addPlayerToDB(PlayerDB(data.getName(),data.getId()))
                    }
                    viewState.changeFragment()
                    Log.d("pl","Дошли")
                }
            }
        }
    } else {
        viewState.showSnackbar("Пустой запрос!")
    }

    override fun onDestroy() {
        if (job != null) {
            job!!.cancel()
        }
    }
}
