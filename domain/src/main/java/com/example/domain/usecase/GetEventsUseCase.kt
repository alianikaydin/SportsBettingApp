package com.example.domain.usecase

import com.example.domain.model.Event
import com.example.domain.repository.SportsRepository
import com.example.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repository: SportsRepository
) {
    operator fun invoke(): Flow<NetworkResult<List<Event>>> = flow {
        val allEvents = mutableListOf<Event>()
        repository.getSports().collect { sportsResult ->
            when (sportsResult) {
                is NetworkResult.Success -> {
                    val sports = sportsResult.data

                    for (sport in sports) {
                        repository.getEventsBySportKey(sport.key).collect { eventsResult ->
                            when (eventsResult) {
                                is NetworkResult.Success -> {
                                    eventsResult.data.let { allEvents.addAll(it) }
                                }
                                is NetworkResult.Error -> {
                                    emit(NetworkResult.Error("Failed to fetch events for ${sport.key}: ${eventsResult.message}"))
                                    return@collect
                                }
                                is NetworkResult.Loading -> {
                                    emit(NetworkResult.Loading)
                                }                            }
                        }
                    }

                    emit(NetworkResult.Success(allEvents))
                }

                is NetworkResult.Error -> {
                    emit(NetworkResult.Error("Failed to fetch sports: ${sportsResult.message}"))
                }
                is NetworkResult.Loading -> {
                    emit(NetworkResult.Loading)
                }
            }
        }
    }.flowOn(Dispatchers.IO)}