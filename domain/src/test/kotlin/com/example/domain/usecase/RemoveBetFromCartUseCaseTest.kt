package com.example.domain.usecase

import com.example.domain.repository.CartRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveBetFromCartUseCaseTest {

    private val repository = mockk<CartRepository>(relaxed = true)
    private val useCase = RemoveBetFromCartUseCase(repository)

    @Test
    fun `invoke should call repository removeBet`() = runTest {
        val bet = dummyBet()

        useCase.invoke(bet)

        coVerify { repository.removeBet(bet) }
    }
}