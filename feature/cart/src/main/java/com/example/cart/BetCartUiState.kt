package com.example.cart

import com.example.domain.model.Bet

data class BetCartUiState(
    val bets: List<Bet> = emptyList(),
    val totalOdds: Double = 1.0
)