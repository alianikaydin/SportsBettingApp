package com.example.domain.model

import com.example.network.dto.BookmakerDto
import com.example.network.dto.EventDto
import com.example.network.dto.MarketDto
import com.example.network.dto.OutcomeDto

data class Event(
    val id: String,
    val sportKey: String,
    val sportTitle: String,
    val commenceTime: String,
    val homeTeam: String,
    val awayTeam: String,
    val bookmakers: List<Bookmaker>
)

data class Bookmaker(
    val key: String,
    val title: String,
    val lastUpdate: String,
    val markets: List<Market>
)

data class Market(
    val key: String,
    val outcomes: List<Outcome>
)

data class Outcome(
    val name: String,
    val price: Double
)

fun EventDto.toDomain(): Event = Event(
    id = id.orEmpty(),
    sportKey = sportKey.orEmpty(),
    sportTitle = sportTitle.orEmpty(),
    commenceTime = commenceTime.orEmpty(),
    homeTeam = homeTeam.orEmpty(),
    awayTeam = awayTeam.orEmpty(),
    bookmakers = bookmakers?.map { it.toDomain() } ?: emptyList()
)

fun BookmakerDto.toDomain(): Bookmaker = Bookmaker(
    key = key.orEmpty(),
    title = title.orEmpty(),
    lastUpdate = lastUpdate.orEmpty(),
    markets = markets?.map { it.toDomain() } ?: emptyList()
)

fun MarketDto.toDomain(): Market = Market(
    key = key.orEmpty(),
    outcomes = outcomes?.map { it.toDomain() } ?: emptyList()
)

fun OutcomeDto.toDomain(): Outcome = Outcome(
    name = name.orEmpty(),
    price = price ?: 0.0
)