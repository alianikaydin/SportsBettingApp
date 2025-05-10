package com.example.domain.model

data class Bet(
    val eventId: String,
    val eventTitle: String,
    val outcomeName: String,
    val odds: Double,
    val bookmaker: String,
    val market: String,
    val commenceTime: String
)