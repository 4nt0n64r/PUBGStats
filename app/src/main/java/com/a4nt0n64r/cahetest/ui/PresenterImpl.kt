package com.a4nt0n64r.cahetest.ui

import android.util.Log
import com.a4nt0n64r.cahetest.core.CacheTestApplication
import com.a4nt0n64r.cahetest.domain.model.Player
import com.a4nt0n64r.cahetest.domain.repository.Repository
import com.a4nt0n64r.cahetest.ui.base.Presenter
import com.a4nt0n64r.cahetest.ui.base.View
import kotlinx.coroutines.*
import javax.inject.Inject

class PresenterImpl : Presenter {

    @Inject lateinit var repository: Repository

    private var job: Job? = null

    private lateinit var mainView: View

    override fun onDeleteButtonWasClicked(name: String) {
        job == CoroutineScope(Dispatchers.Main).launch {
            repository.deletePlayer(name)
            withContext(Dispatchers.IO) {
                mainView.showSnackbar("deleted $name")
            }
        }
    }

    override fun onSaveButtonWasClicked(name: String, data: String) {
        job == CoroutineScope(Dispatchers.Main).launch {
            if (name != "" && data != "") {
                repository.savePlayer(Player(name, data))
                withContext(Dispatchers.IO) {
                    mainView.showSnackbar("save ${name}, ${data}")
                }
            } else {
                mainView.showSnackbar("Enter name and data please!")
            }

        }
    }

    override fun onFindButtonWasClicked(name: String) {
        if (name != "") {
            val player = CoroutineScope(Dispatchers.Main).async {
                repository.findPlayer(name)
            }
            job == CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    mainView.showSnackbar("find ${player.await()}")
                    mainView.fillName(player.await().name)
                    mainView.fillData(player.await().data)
                }
            }
        } else {
            mainView.showSnackbar("Empty find request!")
        }

    }

    override fun onShowButtonWasClicked() {
        job == CoroutineScope(Dispatchers.Main).launch {
            val players = repository.getAllPlayers()
            withContext(Dispatchers.IO) {
                mainView.showSnackbar("show all")
                var names: String = ""
                var data: String = ""
                for (pl in players) {
                    names += pl.name
                    data += pl.data
                }
                mainView.fillName(names)
                mainView.fillData(data)
            }

        }
    }

    override fun setView(view: View) {
        this.mainView = view
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