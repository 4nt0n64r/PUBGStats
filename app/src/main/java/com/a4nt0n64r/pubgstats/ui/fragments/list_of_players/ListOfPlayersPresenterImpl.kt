package com.a4nt0n64r.pubgstats.ui.fragments.list_of_players

import android.util.Log
import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.PlayerDBUI
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository
import com.a4nt0n64r.pubgstats.ui.ADD_PLAYER
import com.a4nt0n64r.pubgstats.ui.base.AbstractListOfPlayersPresenter
import kotlinx.coroutines.*
import moxy.InjectViewState


@InjectViewState
class ListOfPlayersPresenterImpl(
    private val localRepository: LocalRepository
) : AbstractListOfPlayersPresenter() {

    override fun letsAddNewPlayer() {
        viewState.changeFragment(ADD_PLAYER)
    }

    private var job: Job? = null

    lateinit var listOfPlayersUI: List<PlayerDBUI>

    var minusButtonIsShown = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listOfPlayersUI = emptyList()
    }

    override fun requestPlayersFromDB() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val listOfPlayers = CoroutineScope(Dispatchers.IO).async {
                localRepository.getAllPlayersFromDB()
            }
            listOfPlayersUI =
                listOfPlayers.await().map { PlayerDBUI(it.name, it.id, false) }
            withContext(Dispatchers.Main) {
                viewState.showPlayers(listOfPlayersUI)
            }
        }
    }

    override fun onPlayerTouched(position: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val listOfPlayersInRV = listOfPlayersUI.map { PlayerDB(it.name,it.id) }
                viewState.showPlayerStatistics(listOfPlayersInRV[position])
            }
        }
    }

    override fun onPlayerPressed(position: Int) {
        job = CoroutineScope(Dispatchers.Main).launch {
            listOfPlayersUI[position].isSelected = !listOfPlayersUI[position].isSelected
            viewState.showPlayers(listOfPlayersUI)

            val selectedPlayers = listOfPlayersUI.filter { item -> item.isSelected }

            if (!selectedPlayers.isNullOrEmpty()) {
                viewState.showMinusButton()
                minusButtonIsShown = true
            } else {
                viewState.hideMinusButton()
                minusButtonIsShown = false
            }
        }

    }



    override fun deleteSelectedPlayers() {
        job = CoroutineScope(Dispatchers.IO).launch {
            for (item in listOfPlayersUI) {
                if (item.isSelected) {
                    val playerToDel = PlayerDB(item.name, item.id)
                    localRepository.deletePlayerFromDB(playerToDel)
                }
            }
            val listOfPlayers = CoroutineScope(Dispatchers.IO).async {
                localRepository.getAllPlayersFromDB()
            }
            listOfPlayersUI =
                listOfPlayers.await().map { PlayerDBUI(it.name, it.id, false) }
            withContext(Dispatchers.Main) {
                viewState.showPlayers(listOfPlayersUI)
                viewState.hideMinusButton()
            }
        }
    }

    override fun onDestroy() {
        if (job != null) {
            job!!.cancel()
        }
    }
}