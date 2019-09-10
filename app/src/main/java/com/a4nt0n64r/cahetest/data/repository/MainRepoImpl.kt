package com.a4nt0n64r.cahetest.data.repository

import com.a4nt0n64r.cahetest.data.source.db.MyDataBase
import com.a4nt0n64r.cahetest.data.source.db.PlayerDao
import com.a4nt0n64r.cahetest.domain.model.Player
import com.a4nt0n64r.cahetest.domain.repository.Repository
import javax.inject.Inject

class MainRepoImpl @Inject constructor(private val database: MyDataBase): Repository {

    override suspend fun deletePlayer(name: String) {
        database.playerDAO().deleteByName(name)
//        dao.deleteByName(name)
    }

    override suspend fun findPlayer(name: String): Player {
        return database.playerDAO().findByName(name)
//        return dao.findByName(name)
    }

    override suspend fun getAllPlayers(): List<Player> {
        return database.playerDAO().selectAll()
//        return dao.selectAll()
    }

    override suspend fun savePlayer(player: Player) {
        database.playerDAO().saveUser(player)
//        dao.saveUser(player)
    }
}