package com.example.domain.usecase

import com.example.domain.model.Event
import com.example.domain.model.Sport
import com.example.domain.repository.SportsRepository
import com.example.network.NetworkResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetEventsUseCaseTest {

    private lateinit var repository: SportsRepository
    private lateinit var useCase: GetEventsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetEventsUseCase(repository)
    }

    @Test
    fun `invoke emits success when all sports and events are fetched successfully`() = runTest {
        // Given
        val sports = listOf(Sport(key = "football", title = "Football", description = "description"))
        val events = listOf(Event(id = "e1", sportKey = "football", homeTeam = "A", awayTeam = "B", bookmakers = emptyList(), commenceTime = "", sportTitle = "Title"))

        coEvery { repository.getSports() } returns flowOf(NetworkResult.Success(sports))
        coEvery { repository.getEventsBySportKey("football") } returns flowOf(NetworkResult.Success(events))

        // When
        val result = useCase().toList()

        // Then
        assertThat(result.last()).isInstanceOf(NetworkResult.Success::class.java)
        val success = result.last() as NetworkResult.Success
        assertThat(success.data).hasSize(1)
        assertThat(success.data[0].id).isEqualTo("e1")
    }

    @Test
    fun `invoke emits error when getSports fails`() = runTest {
        coEvery { repository.getSports() } returns flowOf(NetworkResult.Error("network error"))

        val result = useCase().toList()

        assertThat(result.first()).isInstanceOf(NetworkResult.Error::class.java)
        val error = result.first() as NetworkResult.Error
        assertThat(error.message).contains("Failed to fetch sports")
    }

    @Test
    fun `invoke emits error when getEventsBySportKey fails`() = runTest {
        val sports = listOf(Sport(key = "basketball", title = "Basketball", description = "description"))

        coEvery { repository.getSports() } returns flowOf(NetworkResult.Success(sports))
        coEvery { repository.getEventsBySportKey("basketball") } returns flowOf(NetworkResult.Error("failed"))

        val result = useCase().toList()

        assertThat(result.any { it is NetworkResult.Error }).isTrue()
        val error = result.find { it is NetworkResult.Error } as NetworkResult.Error
        assertThat(error.message).contains("Failed to fetch events")
    }
}
