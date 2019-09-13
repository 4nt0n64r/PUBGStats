package com.a4nt0n64r.cahetest.data.source.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.a4nt0n64r.cahetest.domain.model.Player

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlayer(player: Player)

    @Query("DELETE FROM players WHERE name_field = :name")
    fun deleteByName(name: String)

    @Query("SELECT name_field,data_field FROM players")
    fun selectAll(): List<Player>

    @Query("SELECT name_field,data_field FROM players WHERE name_field = :name")
    fun findByName(name: String): Player

}