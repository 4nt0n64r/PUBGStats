package com.a4nt0n64r.cahetest.ui

import android.provider.Contacts
import android.util.Log
import com.a4nt0n64r.cahetest.data.repository.MainRepoImpl
import com.a4nt0n64r.cahetest.data.source.db.PlayerDao
import com.a4nt0n64r.cahetest.domain.model.Player
import com.a4nt0n64r.cahetest.domain.repository.Repository
import com.a4nt0n64r.cahetest.ui.base.Presenter
import com.a4nt0n64r.cahetest.ui.base.View
import kotlinx.coroutines.*
import java.util.*

//Обрати внимание на аргументы конструктора - мы передаем экземпляр View,
// а Repository просто создаём конструктором.
//Компоненты MVP приложения

class MainPresenterImpl() : Presenter {

    private var job: Job? = null

    private val repository:Repository = MainRepoImpl()

    private lateinit var mainView: View


    override fun onDeleteButtonWasClicked(name: String) {
        job == CoroutineScope(Dispatchers.Main).launch {
            repository.deletePlayer(name)
            withContext(Dispatchers.IO) {
                mainView.showSnackbar("deleted $name")
            }
        }
    }

    override fun onSaveButtonWasClicked(name:String,data:String) {
        job == CoroutineScope(Dispatchers.Main).launch {
            if (name != "" && data != ""){
                repository.savePlayer(Player(name,data))
                withContext(Dispatchers.IO) {
                    mainView.showSnackbar("save ${name}, ${data}")
                }
            }else{
                mainView.showSnackbar("Enter name and data please!")
            }

        }
    }

    override fun onFindButtonWasClicked(name: String) {
        if (name != ""){
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
        }else{
            mainView.showSnackbar("Empty find request!")
        }

    }

    override fun onShowButtonWasClicked() {
        job == CoroutineScope(Dispatchers.Main).launch {
            val players = repository.getAllPlayers()
            withContext(Dispatchers.IO) {
                mainView.showSnackbar("show all")
                var names:String = ""
                var data:String = ""
                for (pl in players){
                    names = names +pl.name
                    data = data +pl.data
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
        /**
         * Если бы мы работали например с RxJava, в этом классе стоило бы отписываться от подписок
         * Кроме того, при работе с другими методами асинхронного андроида,здесь мы боремся с утечкой контекста
         */
        job!!.cancel()
    }

    companion object {
        val TAG = "MainPresenterImpl"
    }
}

fun log(msg: String) {
    Log.d("TAG", msg)
}