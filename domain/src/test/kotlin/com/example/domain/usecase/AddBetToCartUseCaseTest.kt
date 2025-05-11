package com.example.domain.usecase

import com.example.domain.model.Bet
import com.example.domain.repository.CartRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddBetToCartUseCaseTest {

    private val repository = mockk<CartRepository>(relaxed = true)
    private val useCase = AddBetToCartUseCase(repository)

    @Test
    fun `invoke should call repository addBet`() = runTest {
        val bet = dummyBet()

        useCase.invoke(bet)

        coVerify { repository.addBet(bet) }
    }
}

fun dummyBet() = Bet(
    eventId = "e1",
    eventTitle = "Real Madrid vs Barcelona",
    outcomeName = "Real Madrid",
    odds = 2.1,
    bookmaker = "bet365",
    market = "h2h",
    commenceTime = "2025-05-15T19:00:00Z"
)