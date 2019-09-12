package com.a4nt0n64r.cahetest.data.repository

import com.a4nt0n64r.cahetest.data.source.db.MyDatabase
import com.a4nt0n64r.cahetest.data.source.db.PlayerDao
import com.a4nt0n64r.cahetest.domain.model.Player
import com.a4nt0n64r.cahetest.domain.repository.Repository
import javax.inject.Inject

class RepoImpl : Repository {

    @Inject lateinit var dao: PlayerDao

    override suspend fun deletePlayer(name: String) {
        dao.deleteByName(name)
    }

    override suspend fun findPlayer(name: String): Player {
        return dao.findByName(name)
    }

    override suspend fun getAllPlayers(): List<Player> {
        return dao.selectAll()
    }

    override suspend fun savePlayer(player: Player) {
        dao.saveUser(player)
    }
}