package com.example.data.repository


import com.example.data.BuildConfig.API_KEY
import com.example.domain.model.Event
import com.example.domain.model.Sport
import com.example.domain.model.toDomain
import com.example.domain.repository.SportsRepository
import com.example.network.NetworkResult
import com.example.network.SportsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val api: SportsApi
) : SportsRepository {

    override suspend fun getSports(): Flow<NetworkResult<List<Sport>>> = flow {
        emit(NetworkResult.Loading)
        try {
            val sports = api.getSports(API_KEY)
                .filter { !it.key.isNullOrBlank() && !it.title.isNullOrBlank() && !it.description.isNullOrBlank() }
                .map {
                    it.toDomain()
                }
            emit(NetworkResult.Success(sports))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
    }

    override suspend fun getEventsBySportKey(sportKey: String): Flow<NetworkResult<List<Event>>> = flow {
        emit(NetworkResult.Loading)
        try {
            val events = api.getOdds(sportKey = sportKey, apiKey =  API_KEY)
                .filter { !it.id.isNullOrBlank() && !it.sportKey.isNullOrBlank() && !it.commenceTime.isNullOrBlank() && !it.homeTeam.isNullOrBlank() && !it.awayTeam.isNullOrBlank() && !it.bookmakers.isNullOrEmpty() && !it.sportTitle.isNullOrBlank() }
                .map {
                    it.toDomain()
                }
            emit(NetworkResult.Success(events))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
    }
}