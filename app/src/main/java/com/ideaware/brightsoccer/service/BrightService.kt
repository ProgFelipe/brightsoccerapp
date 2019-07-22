package com.ideaware.brightsoccer.service

import com.ideaware.brightsoccer.service.model.Match
import retrofit2.Response
import retrofit2.http.GET

interface BrightService {

    @GET("fixtures.json")
    suspend fun getFixture(): Response<List<Match>>

    @GET("results.json")
    suspend fun getResults(): Response<List<Match>>
}