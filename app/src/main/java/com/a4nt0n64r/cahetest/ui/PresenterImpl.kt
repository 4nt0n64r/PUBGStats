package com.a4nt0n64r.cahetest.ui

import com.a4nt0n64r.cahetest.domain.model.Player
import com.a4nt0n64r.cahetest.domain.repository.Repository
import com.a4nt0n64r.cahetest.network.NetworkRepository
import com.a4nt0n64r.cahetest.ui.base.Presenter
import com.a4nt0n64r.cahetest.ui.base.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.*

@InjectViewState
class PresenterImpl(
    private val repository: Repository,
    private val cloudRepository: NetworkRepository
) : MvpPresenter<View>(),Presenter {


    private var job: Job? = null

    override fun onDeleteButtonWasClicked(name: String) {
        if (name != "") {
            job == CoroutineScope(Dispatchers.IO).launch {
                val player = repository.findPlayer(name)
                if (player != null) {
                    repository.deletePlayer(name)
                    withContext(Dispatchers.Main) {
                        viewState.showSnackbar("deleted $name")
                    }
                } else {
                    withContext(Dispatchers.Main) { viewState.showSnackbar("There's no players with name $name") }
                }
            }
        } else {
            job == CoroutineScope(Dispatchers.Main).launch {
                viewState.showSnackbar("Empty delete request!")
            }
        }
    }

    override fun onSaveButtonWasClicked(name: String, data: String) {
        job == CoroutineScope(Dispatchers.IO).launch {
            if (name != "" && data != "") {
                repository.savePlayer(Player(name, data))
                withContext(Dispatchers.Main) {
                    viewState.showSnackbar("save ${name} ${data}")
                }
            } else {
                job == CoroutineScope(Dispatchers.Main).launch {
                    viewState.showSnackbar("Empty find request!")
                }
            }
        }
    }

    override fun onFindButtonWasClicked(name: String) {
        if (name != "") {
            val player = CoroutineScope(Dispatchers.IO).async {
                repository.findPlayer(name)
            }
            job == CoroutineScope(Dispatchers.IO).launch {
                if (player.await() != null) {
                    viewState.showSnackbar("find ${player.await()}")
                    viewState.fillName(player.await().name)
                    viewState.fillData(player.await().data)
                } else {
                    withContext(Dispatchers.Main) { viewState.showSnackbar("There's no players with name $name") }
                }
            }
        } else {
            job == CoroutineScope(Dispatchers.Main).launch {
                viewState.showSnackbar("Empty find request!")
            }
        }
    }

    override fun onShowButtonWasClicked() {
        job == CoroutineScope(Dispatchers.IO).launch {
            val players = repository.getAllPlayers()
            if (players != null) {
                withContext(Dispatchers.Main) { viewState.showSnackbar("show all") }
                var names: String = ""
                var data: String = ""
                for (pl in players) {
                    names += (" " + pl.name)
                    data += (" " + pl.data)
                }
                viewState.fillName(names)
                viewState.fillData(data)
            } else {
                withContext(Dispatchers.Main) { viewState.showSnackbar("There's no players!") }
            }
        }
    }

    override fun onNetButtonWasClicked() {
        job == CoroutineScope(Dispatchers.IO).launch {

            var name = ""
            var data = ""

            cloudRepository.getPlayer { responce ->
                name = responce.player.name
                data = responce.player.data
                val cloudPlayer = Player(name, data)
                CoroutineScope(Dispatchers.Main).launch{viewState.showSnackbar(cloudPlayer.toString())}
            }
        }
    }

    override fun onDestroy() {
        if (job != null) {
            job!!.cancel()
        }
    }

    companion object {
        val TAG = "PresenterImpl"
    }
}