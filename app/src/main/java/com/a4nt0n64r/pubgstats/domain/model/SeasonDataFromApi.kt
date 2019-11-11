package com.a4nt0n64r.pubgstats.domain.model

import com.google.gson.annotations.SerializedName

data class SeasonsDataFromApi(
    @SerializedName("data")
    val seasons: List<Season>
)

data class Season(
    val id: String,
    @SerializedName("attributes")
    val attributes: SeasonAttributes
) {
    override fun toString(): String {
        return id
    }
}

data class SeasonAttributes(
    val isCurrentSeason: String,
    val isOffseason: String
)

