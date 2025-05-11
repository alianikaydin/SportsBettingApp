package com.example.core

import com.example.core.ui.extensions.calculateTotalOdds
import com.example.domain.model.Bet
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class BetCalculateExtensionsTest {

    @Test
    fun `empty list should return 1_0`() {
        val bets = emptyList<Bet>()

        val result = bets.calculateTotalOdds()

        assertThat(result).isEqualTo(1.0)
    }

    @Test
    fun `single bet should return its odds`() {
        val bets = listOf(
            Bet("e1", "Team A vs Team B", "Team A", 2.0, "bet365", "h2h", "2025-05-10")
        )

        val result = bets.calculateTotalOdds()

        assertThat(result).isEqualTo(2.0)
    }

    @Test
    fun `multiple bets should return multiplied odds`() {
        val bets = listOf(
            Bet("e1", "Match 1", "Team A", 1.5, "b1", "h2h", ""),
            Bet("e2", "Match 2", "Team B", 2.0, "b2", "h2h", ""),
            Bet("e3", "Match 3", "Team C", 1.2, "b3", "h2h", "")
        )

        val result = bets.calculateTotalOdds()

        assertThat(result).isWithin(0.01).of(3.6) // 1.5 * 2.0 * 1.2 = 3.6
    }
}