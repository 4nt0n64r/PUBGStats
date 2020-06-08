package com.a4nt0n64r.pubgstats.network

import com.a4nt0n64r.pubgstats.domain.model.PlayerDataFromApi
import com.a4nt0n64r.pubgstats.domain.model.StatisticsFromApi
import com.a4nt0n64r.pubgstats.domain.model.SeasonsDataFromApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers(
        "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4MGEwZTNlMC0xZmQ3LTAxMzctMGNhNS0yMTY0MTZkZjQ2N2MiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTUxNjEzNDE1LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImZpbmFsX3Byb2plY3QifQ.Pd1DbJrDFoRhoHG4lj84kNUtt2GWZqklYIYvR9POCIg",
        "Accept: application/vnd.api+json"
    )
    @GET("steam/players/")
    fun getPlayer(@Query("filter[playerNames]") nameOfPlayer: String): Call<PlayerDataFromApi>


    @Headers(
        "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4MGEwZTNlMC0xZmQ3LTAxMzctMGNhNS0yMTY0MTZkZjQ2N2MiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTUxNjEzNDE1LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImZpbmFsX3Byb2plY3QifQ.Pd1DbJrDFoRhoHG4lj84kNUtt2GWZqklYIYvR9POCIg",
        "Accept: application/vnd.api+json"
    )
    @GET("steam/seasons/")
    fun getSeasons(): Call<SeasonsDataFromApi>

    @Headers(
        "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4MGEwZTNlMC0xZmQ3LTAxMzctMGNhNS0yMTY0MTZkZjQ2N2MiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTUxNjEzNDE1LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImZpbmFsX3Byb2plY3QifQ.Pd1DbJrDFoRhoHG4lj84kNUtt2GWZqklYIYvR9POCIg",
        "Accept: application/vnd.api+json"
    )
    @GET("steam/players/{playerId}/seasons/{seasonId}")
    fun getSeasonStats(@Path("playerId") playerId: String,@Path("seasonId") seasonId: String): Call<StatisticsFromApi>

}
//    "https://api.pubg.com/shards/$platform/players/$playerId/seasons/{seasonId}"

//base : "https://api.pubg.com/shards/"
//get player by name "https://api.pubg.com/shards/steam/players?filter[playerNames]=CHEEL40000"
//get season stats by players ID "https://api.pubg.com/shards/steam/players/account.c0e530e9b7244b358def282782f893af/seasons/division.bro.official.pc-2018-06"


// https://api.pubg.com/shards/steam/players/account.c0e530e9b7244b358def282782f893af/seasons/division.bro.official.pc-2018-06
// https://api.pubg.com/steam/players/account.05d02987df274d23b404bb3373f7825c/seasons/division.bro.official.pc-2018-06

