package com.example.betting.betbulletin

import com.example.domain.model.Event
import com.example.domain.usecase.GetEventsUseCase
import com.example.network.NetworkResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
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
class BetBulletinViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getEventsUseCase: GetEventsUseCase
    private lateinit var viewModel: BetBulletinViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getEventsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchEvents updates state with events on success`() = runTest {
        val events = listOf(
            Event(
                id = "e1",
                sportKey = "football",
                homeTeam = "Real Madrid",
                awayTeam = "Barcelona",
                commenceTime = "2025-05-11T15:00:00Z",
                bookmakers = emptyList(),
                sportTitle = "Title"
            )
        )

        coEvery { getEventsUseCase() } returns flowOf(NetworkResult.Success(events))

        viewModel = BetBulletinViewModel(getEventsUseCase)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.eventsResult).isInstanceOf(NetworkResult.Success::class.java)
        assertThat(state.filteredEvents).isEqualTo(events)
    }

    @Test
    fun `onSearchQueryChanged filters correctly`() = runTest {
        Event(
            id = "e1",
            sportKey = "football",
            homeTeam = "Real Madrid",
            awayTeam = "Barcelona",
            commenceTime = "2025-05-11T15:00:00Z",
            bookmakers = emptyList(),
            sportTitle = "Title"
        )
        val events = listOf(
            Event(
                id = "e1",
                sportKey = "football",
                homeTeam = "Fenerbahçe",
                awayTeam = "Galatasaray",
                commenceTime = "2025-05-11T15:00:00Z",
                bookmakers = emptyList(),
                sportTitle = "Title"
            ),
            Event(
                id = "e2",
                sportKey = "football",
                homeTeam = "Trabzonspor",
                awayTeam = "Beşiktaş",
                commenceTime = "2025-05-11T15:00:00Z",
                bookmakers = emptyList(),
                sportTitle = "Title"
            )
        )

        coEvery { getEventsUseCase() } returns flowOf(NetworkResult.Success(events))

        viewModel = BetBulletinViewModel(getEventsUseCase)
        advanceUntilIdle()

        viewModel.onSearchQueryChanged("Fener")

        val filtered = viewModel.uiState.value.filteredEvents
        assertThat(filtered).hasSize(1)
        assertThat(filtered[0].homeTeam).isEqualTo("Fenerbahçe")
    }
}
