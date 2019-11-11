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
    @GET("players/")
    fun getPlayer(@Query("filter[playerNames]") nameOfPlayer: String): Call<PlayerDataFromApi>


    @Headers(
        "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4MGEwZTNlMC0xZmQ3LTAxMzctMGNhNS0yMTY0MTZkZjQ2N2MiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTUxNjEzNDE1LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImZpbmFsX3Byb2plY3QifQ.Pd1DbJrDFoRhoHG4lj84kNUtt2GWZqklYIYvR9POCIg",
        "Accept: application/vnd.api+json"
    )
    @GET("seasons/")
    fun getSeasons(): Call<SeasonsDataFromApi>

    @Headers(
        "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4MGEwZTNlMC0xZmQ3LTAxMzctMGNhNS0yMTY0MTZkZjQ2N2MiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTUxNjEzNDE1LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6ImZpbmFsX3Byb2plY3QifQ.Pd1DbJrDFoRhoHG4lj84kNUtt2GWZqklYIYvR9POCIg",
        "Accept: application/vnd.api+json"
    )
    @GET("seasons/{seasonId}")
    fun getSeasonStats(@Path("seasonId") seasonId: String): Call<StatisticsFromApi>

}