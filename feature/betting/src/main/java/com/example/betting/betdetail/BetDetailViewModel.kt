package com.example.betting.betdetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analytics.AnalyticsManager
import com.example.domain.model.Bet
import com.example.domain.model.Event
import com.example.domain.usecase.AddBetToCartUseCase
import com.example.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BetDetailViewModel @Inject constructor(
    private val addBetToCartUseCase: AddBetToCartUseCase,
    private val analyticsManager: AnalyticsManager
) : ViewModel() {

    private val _uiState = mutableStateOf<NetworkResult<Event>>(NetworkResult.Loading)
    val uiState: State<NetworkResult<Event>> = _uiState

    fun setEvent(event: Event?) {
        if (event == null) {
            _uiState.value = NetworkResult.Error("Event not found.")
        } else {
            analyticsManager.logMatchDetailOpened(event.id)
            _uiState.value = NetworkResult.Success(event)
        }
    }

    fun addBetToCart(bet: Bet) {
        viewModelScope.launch {
            addBetToCartUseCase(bet)
            analyticsManager.logBetAddedToCart(bet)
        }
    }
}