package com.example.cart

import androidx.lifecycle.ViewModel
import com.example.domain.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BetCartViewModel : ViewModel() {

    private val _cartState = MutableStateFlow(BetCartUiState())
    val cartState: StateFlow<BetCartUiState> = _cartState

    fun addToCart(event: Event) {
        _cartState.update { state ->
            val updatedItems = state.cartItems + event
            state.copy(
                cartItems = updatedItems,
                totalOdds = calculateTotalOdds(updatedItems)
            )
        }
    }

    fun removeFromCart(event: Event) {
        _cartState.update { state ->
            val updatedItems = state.cartItems.filterNot { it.id == event.id }
            state.copy(
                cartItems = updatedItems,
                totalOdds = calculateTotalOdds(updatedItems)
            )
        }
    }

    private fun calculateTotalOdds(events: List<Event>): Double {
        return 0.0
        //TODO: calculation function yaz
    }
}

