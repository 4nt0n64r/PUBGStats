package com.a4nt0n64r.cahetest.domain.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "players")
data class Player(
    @SerializedName("name")
    @ColumnInfo(name = "name_field") val name: String,
    @SerializedName("data")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "data_field") val data: String

)

data class CloudPlayer(
    @SerializedName("player")
    val player: Player
)


