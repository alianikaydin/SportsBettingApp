package com.example.data.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Bet

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey val id: String,
    val eventId: String,
    val eventTitle: String,
    val outcomeName: String,
    val odds: Double,
    val bookmaker: String,
    val market: String,
    val commenceTime: String
)

fun CartEntity.toBet(): Bet = Bet(
    eventId = eventId,
    eventTitle = eventTitle,
    outcomeName = outcomeName,
    odds = odds,
    bookmaker = bookmaker,
    market = market,
    commenceTime = commenceTime
)