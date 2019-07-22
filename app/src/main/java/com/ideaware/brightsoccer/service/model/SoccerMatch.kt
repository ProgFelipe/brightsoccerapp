package com.ideaware.brightsoccer.service.model

import com.google.gson.annotations.SerializedName

data class SoccerMatch(
    val id: Number? = null,
    val type: Type? = null,
    val homeTeam: HomeTeam? = null,
    val awayTeam: AwayTeam? = null,
    val date: String? = "",
    val competitionStage: CompetitionStage? = null,
    val venue: Venue? = null,
    var state: State? = null,
    val score: Score? = null
)

data class AwayTeam(val id: Number?, val name: String?, val shortName: String?, val abbr: String?, val alias: String?)

data class CompetitionStage(val competition: Venue, val stage: String?, val leg: String?)

data class HomeTeam(val id: Number?, val name: String?, val shortName: String?, val abbr: String?, val alias: String?)

data class Venue(val id: Number?, val name: String?)

data class Score(val home: Number?, val away: Number?, val winner: Winner?)

enum class Winner {
    @SerializedName("home")
    Home,
    @SerializedName("away")
    Away
}

enum class State {
    @SerializedName("postponed")
    Postponed,
    @SerializedName("preMatch")
    PreMatch,
    Header
}

enum class Type {
    @SerializedName("FixtureUpcoming")
    FixtureUpcoming
}