package com.example.betting.betbulletin

import com.example.domain.model.Event
import com.example.network.NetworkResult

data class BetBulletinUiState(
    val eventsResult: NetworkResult<List<Event>> = NetworkResult.Loading,
    val searchQuery: String = "",
    val filteredEvents: List<Event> = emptyList()
)