package com.a4nt0n64r.pubgstats.domain.model

import com.google.gson.annotations.SerializedName

data class StatisticsFromApi(
    @SerializedName("data")
    val data: SeasonStatistics
)

data class SeasonStatistics(
    val type: String,
    @SerializedName("attributes")
    val seasonAttributes: StatAttributes
)


data class StatAttributes(
    val gameModeStats: Gamemode
)

data class Gamemode(
    @SerializedName("duo")
    val duo: StatData,
    @SerializedName("duo-fpp")
    val duo_fpp: StatData,
    @SerializedName("solo")
    val solo: StatData,
    @SerializedName("solo-fpp")
    val solo_fpp: StatData,
    @SerializedName("squad")
    val squad: StatData,
    @SerializedName("squad-fpp")
    val squad_fpp: StatData
)

data class StatData(
    val assists: Int,
    val bestRankPoint: Double,
    val boosts: Int,
    val dBNOs: Int,
    val dailyKills: Int,
    val dailyWins: Int,
    val damageDealt: Double,
    val days: Int,
    val headshotKills: Int,
    val heals: Int,
    val killPoints: Int,
    val kills: Int,
    val longestKill: Double,
    val longestTimeSurvived: Double,
    val losses: Int,
    val maxKillStreaks: Int,
    val mostSurvivalTime: Double,
    val rankPoints: Double,
    val rankPointsTitle: String,
    val revives: Int,
    val rideDistance: Double,
    val roadKills: Int,
    val roundMostKills: Int,
    val roundsPlayed: Int,
    val suicides: Int,
    val swimDistance: Double,
    val teamKills: Int,
    val timeSurvived: Double,
    val top10s: Int,
    val vehicleDestroys: Int,
    val walkDistance: Double,
    val weaponsAcquired: Int,
    val weeklyKills: Int,
    val winPoints: Int,
    val wins: Int
)


//интерфейс используется в адаптере для RV
interface StatisticsItem

data class StatisticsHeader(
    val text:String
):StatisticsItem

data class StatisticsPoints(
    val text:String,
    val points:String
):StatisticsItem
