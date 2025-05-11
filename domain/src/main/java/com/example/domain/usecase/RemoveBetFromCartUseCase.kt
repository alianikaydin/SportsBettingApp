package com.example.domain.usecase

import com.example.domain.model.Bet
import com.example.domain.repository.CartRepository
import javax.inject.Inject

class RemoveBetFromCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(bet: Bet) {
        repository.removeBet(bet)
    }
}