package com.a4nt0n64r.cahetest.data.network

import com.a4nt0n64r.cahetest.domain.model.Player
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIservice {

    @GET("")
    fun getPlayerFromCloud(@Query("") file: String): Call<Player>

}