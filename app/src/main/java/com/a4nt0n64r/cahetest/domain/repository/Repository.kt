package com.a4nt0n64r.cahetest.domain.repository

import com.a4nt0n64r.cahetest.domain.model.Player

//Тут описаны все взаимодействия с Model = Бизнес логика = данные
interface Repository {
    suspend fun findPlayer(name: String): Player
    suspend fun deletePlayer(name: String)
    suspend fun getAllPlayers(): List<Player>
    suspend fun savePlayer(player: Player)
}