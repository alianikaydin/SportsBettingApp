package com.example.network

import com.example.network.dto.EventDto
import com.example.network.dto.SportDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SportsApi {
    @GET("sports")
    suspend fun getSports(@Query("apiKey") apiKey: String): List<SportDto>

    @GET("sports/{sport_key}/odds")
    suspend fun getOdds(
        @Path("sport_key") sportKey: String,
        @Query("regions") regions: String = "eu",
        @Query("markets") markets: String = "h2h",
        @Query("oddsFormat") oddsFormat: String = "decimal",
        @Query("dateFormat") dateFormat: String = "iso",
        @Query("apiKey") apiKey: String
    ): List<EventDto>


}
