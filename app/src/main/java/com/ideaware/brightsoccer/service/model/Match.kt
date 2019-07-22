package com.ideaware.brightsoccer.service.model

data class Match(
    val id: Number?,
    val type: Type?,
    val homeTeam: HomeTeam?,
    val awayTeam: AwayTeam?,
    val date: String?,
    val competitionStage: CompetitionStage?,
    val venue: Venue?,
    val state: State?
)

data class AwayTeam(val id: Number?, val name: String?, val shortName: String?, val abbr: String?, val alias: String?)

data class CompetitionStage(val competition: Venue, val stage: String?, val leg: String?)

data class HomeTeam(val id: Number?, val name: String?, val shortName: String?, val abbr: String?, val alias: String?)

data class Venue(val id: Number?, val name: String?)

enum class State {
    Postponed,
    PreMatch
}

enum class Type {
    FixtureUpcoming
}