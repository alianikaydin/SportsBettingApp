package com.example.cart

import com.example.analytics.AnalyticsManager
import com.example.domain.model.Bet
import com.example.domain.usecase.ObserveCartItemsUseCase
import com.example.domain.usecase.RemoveBetFromCartUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BetCartViewModelTest {

    private lateinit var observeCartItemsUseCase: ObserveCartItemsUseCase
    private lateinit var removeUseCase: RemoveBetFromCartUseCase
    private lateinit var analyticsManager: AnalyticsManager

    private lateinit var viewModel: BetCartViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        observeCartItemsUseCase = mockk()
        removeUseCase = mockk(relaxed = true)
        analyticsManager = mockk(relaxed = true)

        every { observeCartItemsUseCase() } returns flowOf(listOf(dummyBet()))

        viewModel = BetCartViewModel(
            observeCartItemsUseCase,
            removeUseCase,
            analyticsManager
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cartState should update with bets and totalOdds`() = runTest {
        advanceUntilIdle()

        val state = viewModel.cartState.value

        assertEquals(1, state.bets.size)
        assertEquals("e1", state.bets[0].eventId)
        assertEquals(2.0, state.totalOdds, 0.01)
    }

    @Test
    fun `removeBet should call useCase and analytics`() = runTest {
        val bet = dummyBet()

        viewModel.removeBet(bet)
        advanceUntilIdle()

        coVerify { removeUseCase(bet) }
        verify { analyticsManager.logBetRemovedFromCart(bet) }
    }
}

fun dummyBet() = Bet(
    eventId = "e1",
    eventTitle = "Real Madrid vs Barcelona",
    outcomeName = "Real Madrid",
    odds = 2.0,
    bookmaker = "bet365",
    market = "h2h",
    commenceTime = "2025-05-15T19:00:00Z"
)