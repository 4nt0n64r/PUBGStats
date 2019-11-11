package com.a4nt0n64r.pubgstats.domain.model

import com.google.gson.annotations.SerializedName

// парсим ответ от АПИ ------
data class PlayerDataFromApi(
    @SerializedName("data")
    //хоть тут и лист, но заказываем мы одного игрока по имени,
    //так приходится делать, т.к. апи подразумевает запрос
    //списка имен, иначе получить id игрока нельзя :(
    //id нужен уже для всех остальных запросов
    val player: List<Player>

){

    fun getId(): String {
        return player[0].id
    }

    fun getName(): String {
        return player[0].attributes.name
    }
}

data class Player(
    @SerializedName("id")
    val id: String,
    @SerializedName("attributes")
    val attributes: PlayerAttributes
)

data class PlayerAttributes(
    @SerializedName("name")
    val name: String
)
// -----




