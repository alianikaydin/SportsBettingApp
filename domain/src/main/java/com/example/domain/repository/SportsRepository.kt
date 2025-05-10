package com.example.domain.repository

import com.example.domain.model.Event
import com.example.domain.model.Sport
import com.example.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface SportsRepository {
    suspend fun getSports(): Flow<NetworkResult<List<Sport>>>
    suspend fun getEventsBySportKey(sportKey: String): Flow<NetworkResult<List<Event>>>
}