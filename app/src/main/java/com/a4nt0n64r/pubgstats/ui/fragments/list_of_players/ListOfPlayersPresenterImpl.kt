package com.a4nt0n64r.pubgstats.ui.fragments.list_of_players

import com.a4nt0n64r.pubgstats.domain.model.PlayerDB
import com.a4nt0n64r.pubgstats.domain.model.PlayerUI
import com.a4nt0n64r.pubgstats.domain.repository.LocalRepository
import com.a4nt0n64r.pubgstats.ui.ADD_PLAYER
import com.a4nt0n64r.pubgstats.ui.base.AbstractListOfPlayersPresenter
import com.a4nt0n64r.pubgstats.ui.fragments.add_player.NO_INTERNET
import kotlinx.coroutines.*
import moxy.InjectViewState


@InjectViewState
class ListOfPlayersPresenterImpl(
    private val localRepository: LocalRepository
) : AbstractListOfPlayersPresenter() {

    private val job: Job by lazy { SupervisorJob() }

    override fun letsAddNewPlayer() {
        viewState.changeFragment(ADD_PLAYER)
    }

    lateinit var listOfPlayersUI: List<PlayerUI>

    var minusButtonIsShown = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listOfPlayersUI = emptyList()
    }

    override fun requestPlayersFromDB() {
        CoroutineScope(Dispatchers.Main + job).launch {
            val listOfPlayers = CoroutineScope(Dispatchers.IO).async {
                localRepository.getAllPlayersFromDB()
            }
            listOfPlayersUI =
                listOfPlayers.await()
                    .map { PlayerUI(it.name, it.id, it.region, it.platform, false) }
            withContext(Dispatchers.Main) {
                if (listOfPlayersUI.isNullOrEmpty()) {
                    viewState.showErrorTextAndImage()
                    viewState.changeFragment(ADD_PLAYER)
                } else {
                    viewState.hideErrorTextAndImage()
                    viewState.showPlayers(listOfPlayersUI)
                }
            }
        }
    }

    override fun onPlayerTouched(position: Int, isConnected: Boolean) {
        if (isConnected) {
            CoroutineScope(Dispatchers.Main + job).launch {
                withContext(Dispatchers.Main) {
                    val listOfPlayersInRV =
                        listOfPlayersUI.map { PlayerDB(it.name, it.id, it.region, it.platform) }
                    viewState.showPlayerStatistics(listOfPlayersInRV[position])
                }
            }
        } else {
            viewState.showSnackbar(NO_INTERNET)
        }
    }

    override fun onPlayerPressed(position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            listOfPlayersUI[position].isSelected = !listOfPlayersUI[position].isSelected
            viewState.showPlayers(listOfPlayersUI)

            val selectedPlayers = listOfPlayersUI.filter { item -> item.isSelected }

            if (!selectedPlayers.isNullOrEmpty()) {
                viewState.showMinusButton()
                minusButtonIsShown = true
            } else {
                viewState.hideMinusButton()
                viewState.selectionModeOff()
                minusButtonIsShown = false
            }
        }

    }


    override fun deleteSelectedPlayers() {
        CoroutineScope(Dispatchers.IO + job).launch {
            for (item in listOfPlayersUI) {
                if (item.isSelected) {
                    val playerToDel = PlayerDB(item.name, item.id, item.region, item.platform)
                    localRepository.deletePlayerFromDB(playerToDel)
                }
            }
            val listOfPlayers = CoroutineScope(Dispatchers.IO).async {
                localRepository.getAllPlayersFromDB()
            }
            listOfPlayersUI =
                listOfPlayers.await()
                    .map { PlayerUI(it.name, it.id, it.region, it.platform, false) }
            withContext(Dispatchers.Main) {
                viewState.showPlayers(listOfPlayersUI)
                viewState.hideMinusButton()
            }
            withContext(Dispatchers.Main) {
                if (listOfPlayersUI.isNullOrEmpty()) {
                    viewState.showErrorTextAndImage()
                }
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
    }
}