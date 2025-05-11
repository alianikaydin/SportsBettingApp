package com.example.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analytics.AnalyticsManager
import com.example.core.ui.extensions.calculateTotalOdds
import com.example.domain.model.Bet
import com.example.domain.usecase.ObserveCartItemsUseCase
import com.example.domain.usecase.RemoveBetFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BetCartViewModel @Inject constructor(
    observeCartItemsUseCase: ObserveCartItemsUseCase,
    private val removeBetFromCartUseCase: RemoveBetFromCartUseCase,
    private val analyticsManager: AnalyticsManager
) : ViewModel() {

    private val _cartState = MutableStateFlow(BetCartUiState())
    val cartState: StateFlow<BetCartUiState> = _cartState

    init {
        observeCartItemsUseCase().onEach { bets ->
            _cartState.value = _cartState.value.copy(
                bets = bets,
                totalOdds = bets.calculateTotalOdds()
            )
        }.launchIn(viewModelScope)
    }

    fun removeBet(bet: Bet) {
        viewModelScope.launch {
            removeBetFromCartUseCase(bet)
            analyticsManager.logBetRemovedFromCart(bet)
        }
    }
}

