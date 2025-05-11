package com.example.analytics

import com.example.domain.model.Bet

interface AnalyticsManager {
    fun logMatchDetailOpened(eventId: String)
    fun logBetAddedToCart(bet: Bet)
    fun logBetRemovedFromCart(bet: Bet)
}