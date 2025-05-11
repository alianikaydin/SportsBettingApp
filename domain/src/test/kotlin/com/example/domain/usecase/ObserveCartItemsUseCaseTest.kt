package com.example.domain.usecase

import com.example.domain.repository.CartRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveCartItemsUseCaseTest {

    private val repository = mockk<CartRepository>()
    private val useCase = ObserveCartItemsUseCase(repository)

    @Test
    fun `invoke should return flow from repository`() = runTest {
        val fakeFlow = flowOf(listOf(dummyBet()))
        every { repository.observeCartItems() } returns fakeFlow

        val result = useCase.invoke().first()

        assertThat(result).hasSize(1)
        assertThat(result[0].eventId).isEqualTo("e1")
    }
}