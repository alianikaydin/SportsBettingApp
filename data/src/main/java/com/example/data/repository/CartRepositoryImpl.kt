package com.example.data.repository

import com.example.data.repository.entities.CartEntity
import com.example.data.repository.entities.toBet
import com.example.data.repository.model.CartDao
import com.example.domain.model.Bet
import com.example.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CartRepository {
    override fun observeCartItems(): Flow<List<Bet>> =
        cartDao.getCartItems().map { entities -> entities.map { it.toBet() } }.flowOn(ioDispatcher)

    override suspend fun addBet(bet: Bet) = withContext(ioDispatcher) {
        cartDao.insertBet(
            CartEntity(
                id = bet.eventId,
                eventId = bet.eventId,
                eventTitle = bet.eventTitle,
                outcomeName = bet.outcomeName,
                odds = bet.odds,
                bookmaker = bet.bookmaker,
                market = bet.market,
                commenceTime = bet.commenceTime
            )
        )
    }

    override suspend fun removeBet(bet: Bet) = withContext(ioDispatcher) {
        cartDao.deleteBet(
            CartEntity(
                id = bet.eventId,
                eventId = bet.eventId,
                eventTitle = bet.eventTitle,
                outcomeName = bet.outcomeName,
                odds = bet.odds,
                bookmaker = bet.bookmaker,
                market = bet.market,
                commenceTime = bet.commenceTime
            )
        )
    }

    override suspend fun clearCart() = withContext(ioDispatcher) {
        cartDao.clearCart()
    }
}