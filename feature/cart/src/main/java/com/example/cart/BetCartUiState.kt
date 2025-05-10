package com.example.cart

import com.example.domain.model.Event

data class BetCartUiState(
    val cartItems: List<Event> = emptyList(),
    val totalOdds: Double = 1.0
)