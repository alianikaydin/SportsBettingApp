package com.example.domain.repository

import com.example.domain.model.Bet
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCartItems(): Flow<List<Bet>>
    suspend fun addBet(bet: Bet)
    suspend fun removeBet(bet: Bet)
    suspend fun clearCart()
}