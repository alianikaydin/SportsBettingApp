package com.example.domain.usecase

import com.example.domain.model.Bet
import com.example.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<List<Bet>> = repository.observeCartItems()
}