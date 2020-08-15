package com.a4nt0n64r.pubgstats.domain.model

import androidx.room.*
import java.sql.Date
import java.time.LocalDate


@Entity(tableName = "players_table")
data class PlayerDB(
    @ColumnInfo(name = "name_field") val name: String,
    @PrimaryKey
    @ColumnInfo(name = "id_field") val id: String,
    @ColumnInfo(name = "region") val region: String,
    @ColumnInfo(name = "platform") val platform: String
)

@Entity(tableName = "seasons_table")
@TypeConverters(DateConverters::class)
data class SeasonDB(
    @PrimaryKey
    @ColumnInfo(name = "id_field")
    val seasonId: String,
    @ColumnInfo(name = "name_field")
    val name: String,
    @ColumnInfo(name = "type_field")
    val isCurrentSeason: Boolean,
    @ColumnInfo(name = "dateOfLastDownload_field")
    val dateOfLastDownload: LocalDate
)


class DateConverters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(str: String?): LocalDate? {
        return LocalDate.parse(str)
    }
}


data class PlayerUI(
    val name: String,
    val id: String,
    val region: String,
    val platform: String,
    var isSelected: Boolean
)

@Entity(tableName = "statistics_table")
@TypeConverters(StatisticsConverters::class, DateConverters::class)
data class StatisticsDB(
    @PrimaryKey
    @ColumnInfo(name = "id_field")
    val playerId: String,
    @ColumnInfo(name = "region_field")
    val region: String,
    @ColumnInfo(name = "soloFpp_field")
    val soloFpp: StatData,
    @ColumnInfo(name = "duoFpp_field")
    val duoFpp: StatData,
    @ColumnInfo(name = "squadFpp_field")
    val squadFpp: StatData,
    @ColumnInfo(name = "soloTpp_field")
    val soloTpp: StatData,
    @ColumnInfo(name = "duoTpp_field")
    val duoTpp: StatData,
    @ColumnInfo(name = "squadTpp_field")
    val squadTpp: StatData,
    @ColumnInfo(name = "lastDownloadStatisticsDate_field")
    val lastDownloadStatisticsDate: LocalDate
)

class StatisticsConverters {
    @TypeConverter
    fun fromStatData(statData: StatData?): String? {
        var str = ""
        str += statData?.assists.toString()
        str += ","
        str += statData?.bestRankPoint.toString()
        str += ","
        str += statData?.boosts.toString()
        str += ","
        str += statData?.dBNOs.toString()
        str += ","
        str += statData?.dailyKills.toString()
        str += ","
        str += statData?.dailyWins.toString()
        str += ","
        str += statData?.damageDealt.toString()
        str += ","
        str += statData?.days.toString()
        str += ","
        str += statData?.headshotKills.toString()
        str += ","
        str += statData?.heals.toString()
        str += ","
        str += statData?.killPoints.toString()
        str += ","
        str += statData?.kills.toString()
        str += ","
        str += statData?.longestKill.toString()
        str += ","
        str += statData?.longestTimeSurvived.toString()
        str += ","
        str += statData?.losses.toString()
        str += ","
        str += statData?.maxKillStreaks.toString()
        str += ","
        str += statData?.mostSurvivalTime.toString()
        str += ","
        str += statData?.rankPoints.toString()
        str += ","
        str += statData?.rankPointsTitle.toString()
        str += ","
        str += statData?.revives.toString()
        str += ","
        str += statData?.rideDistance.toString()
        str += ","
        str += statData?.roadKills.toString()
        str += ","
        str += statData?.roundMostKills.toString()
        str += ","
        str += statData?.roundsPlayed.toString()
        str += ","
        str += statData?.suicides.toString()
        str += ","
        str += statData?.swimDistance.toString()
        str += ","
        str += statData?.teamKills.toString()
        str += ","
        str += statData?.timeSurvived.toString()
        str += ","
        str += statData?.top10s.toString()
        str += ","
        str += statData?.vehicleDestroys.toString()
        str += ","
        str += statData?.walkDistance.toString()
        str += ","
        str += statData?.weaponsAcquired.toString()
        str += ","
        str += statData?.weeklyKills.toString()
        str += ","
        str += statData?.winPoints.toString()
        str += ","
        str += statData?.wins.toString()
        return str
    }

    @TypeConverter
    fun stringToStatData(str: String?): StatData? {
        val statList = str?.toList()
        if (statList != null) {
            return StatData(
                assists = statList[0].toInt(),
                bestRankPoint = statList[1].toDouble(),
                boosts = statList[2].toInt(),
                dBNOs = statList[3].toInt(),
                dailyKills = statList[4].toInt(),
                dailyWins = statList[5].toInt(),
                damageDealt = statList[6].toDouble(),
                days = statList[7].toInt(),
                headshotKills = statList[8].toInt(),
                heals = statList[9].toInt(),
                killPoints = statList[10].toInt(),
                kills = statList[11].toInt(),
                longestKill = statList[12].toDouble(),
                longestTimeSurvived = statList[13].toDouble(),
                losses = statList[14].toInt(),
                maxKillStreaks = statList[15].toInt(),
                mostSurvivalTime = statList[16].toDouble(),
                rankPoints = statList[17].toDouble(),
                rankPointsTitle = statList[18].toString(),
                revives = statList[19].toInt(),
                rideDistance = statList[20].toDouble(),
                roadKills = statList[21].toInt(),
                roundMostKills = statList[22].toInt(),
                roundsPlayed = statList[23].toInt(),
                suicides = statList[24].toInt(),
                swimDistance = statList[25].toDouble(),
                teamKills = statList[26].toInt(),
                timeSurvived = statList[27].toDouble(),
                top10s = statList[28].toInt(),
                vehicleDestroys = statList[29].toInt(),
                walkDistance = statList[30].toDouble(),
                weaponsAcquired = statList[31].toInt(),
                weeklyKills = statList[32].toInt(),
                winPoints = statList[33].toInt(),
                wins = statList[34].toInt()
            )
        } else {
            return StatData(
                0,
                0.0,
                0,
                0,
                0,
                0,
                0.0,
                0,
                0,
                0,
                0,
                0,
                0.0,
                0.0,
                0,
                0,
                0.0,
                0.0,
                "-",
                0,
                0.0,
                0,
                0,
                0,
                0,
                0.0,
                0,
                0.0,
                0,
                0,
                0.0,
                0,
                0,
                0,
                0
            )
        }
    }
}
