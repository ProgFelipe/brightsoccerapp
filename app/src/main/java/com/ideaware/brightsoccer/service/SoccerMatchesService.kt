package com.ideaware.brightsoccer.service

import com.ideaware.brightsoccer.service.model.SoccerMatch
import retrofit2.Response
import retrofit2.http.GET

interface SoccerMatchesService {

    @GET("fixtures.json")
    suspend fun getFixture(): Response<List<SoccerMatch>>

    @GET("results.json")
    suspend fun getResults(): Response<List<SoccerMatch>>
}