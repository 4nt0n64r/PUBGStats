package com.a4nt0n64r.pubgstats.network

import com.a4nt0n64r.pubgstats.domain.model.PlayerDataFromApi
import com.a4nt0n64r.pubgstats.domain.model.StatisticsFromApi
import com.a4nt0n64r.pubgstats.domain.model.SeasonsDataFromApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

@Suppress("MaxLineLength") //Ключ апи
interface ApiService {

    @Headers(
        "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjNzhiY2ZiMC1jZWMyLTAxMzgtODNmZS0wYmM1ZWQ2ZGE2MzciLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTk4OTkzNDY2LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImNoZWVsNDA2OS1nbWFpIn0.HCGf9lFGeMpVTmGt8Yx7_P055PUHOHyPuAIU_WOqulo",
        "Accept: application/vnd.api+json"
    )
    @GET("steam/players/")
    fun getPlayer(@Query("filter[playerNames]") nameOfPlayer: String): Call<PlayerDataFromApi>


    @Headers(
        "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjNzhiY2ZiMC1jZWMyLTAxMzgtODNmZS0wYmM1ZWQ2ZGE2MzciLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTk4OTkzNDY2LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImNoZWVsNDA2OS1nbWFpIn0.HCGf9lFGeMpVTmGt8Yx7_P055PUHOHyPuAIU_WOqulo",
        "Accept: application/vnd.api+json"
    )
    @GET("steam/seasons/")
    fun getSeasons(): Call<SeasonsDataFromApi>

    @Headers(
        "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjNzhiY2ZiMC1jZWMyLTAxMzgtODNmZS0wYmM1ZWQ2ZGE2MzciLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTk4OTkzNDY2LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImNoZWVsNDA2OS1nbWFpIn0.HCGf9lFGeMpVTmGt8Yx7_P055PUHOHyPuAIU_WOqulo",
        "Accept: application/vnd.api+json"
    )
    @GET("steam/players/{playerId}/seasons/{seasonId}")
    fun getSeasonStats(@Path("playerId") playerId: String,@Path("seasonId") seasonId: String): Call<StatisticsFromApi>

}
