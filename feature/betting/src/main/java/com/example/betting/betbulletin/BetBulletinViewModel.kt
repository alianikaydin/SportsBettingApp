package com.example.betting.betbulletin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Event
import com.example.domain.usecase.GetEventsUseCase
import com.example.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BetBulletinViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BetBulletinUiState())
    val uiState: StateFlow<BetBulletinUiState> = _uiState.asStateFlow()

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            getEventsUseCase().collect { result ->
                val filteredEvents = when (result) {
                    is NetworkResult.Success -> result.data
                    else -> emptyList()
                }
                _uiState.update { it.copy(eventsResult = result, filteredEvents = filteredEvents) }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        val trimmed = query.trim()
        _uiState.update { state ->
            val allEvents = (state.eventsResult as? NetworkResult.Success)?.data.orEmpty()
            val filtered = if (trimmed.isNotEmpty()) {
                allEvents.filter {
                    it.homeTeam.contains(trimmed, ignoreCase = true) || it.awayTeam.contains(trimmed, ignoreCase = true)
                }
            } else allEvents

            state.copy(searchQuery = query, filteredEvents = filtered)
        }
    }
}