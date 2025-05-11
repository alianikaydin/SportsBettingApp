package com.example.betting.betdetail

import com.example.analytics.AnalyticsManager
import com.example.domain.model.Bet
import com.example.domain.model.Event
import com.example.domain.usecase.AddBetToCartUseCase
import com.example.network.NetworkResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BetDetailViewModelTest {

    private lateinit var addBetToCartUseCase: AddBetToCartUseCase
    private lateinit var analyticsManager: AnalyticsManager
    private lateinit var viewModel: BetDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        addBetToCartUseCase = mockk(relaxed = true)
        analyticsManager = mockk(relaxed = true)
        viewModel = BetDetailViewModel(addBetToCartUseCase, analyticsManager)
    }


    @Test
    fun `setEvent with null sets error state`() {
        viewModel.setEvent(null)

        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(NetworkResult.Error::class.java)
        assertThat((state as NetworkResult.Error).message).isEqualTo("Event not found.")
    }

    @Test
    fun `setEvent with valid event updates state and logs analytics`() {
        val event = dummyEvent()

        viewModel.setEvent(event)

        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(NetworkResult.Success::class.java)
        assertThat((state as NetworkResult.Success).data.id).isEqualTo(event.id)

        verify { analyticsManager.logMatchDetailOpened(event.id) }
    }

    @Test
    fun `addBetToCart calls useCase and logs analytics`() = runTest {
        val bet = dummyBet()

        viewModel.addBetToCart(bet)
        advanceUntilIdle()
        coVerify { addBetToCartUseCase(bet) }
        verify { analyticsManager.logBetAddedToCart(bet) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

fun dummyEvent() = Event(
    id = "event1",
    sportKey = "football",
    homeTeam = "Team A",
    awayTeam = "Team B",
    commenceTime = "2025-05-11T15:00:00Z",
    bookmakers = emptyList(),
    sportTitle = "Title"
)

fun dummyBet() = Bet(
    eventId = "event1",
    eventTitle = "Team A vs Team B",
    outcomeName = "Team A",
    odds = 1.8,
    bookmaker = "bet365",
    market = "h2h",
    commenceTime = "2025-05-11T15:00:00Z"
)