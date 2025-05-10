package com.example.data.repository.di

import com.example.data.repository.SportsRepositoryImpl
import com.example.domain.repository.SportsRepository
import com.example.network.SportsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSportsRepository(
        sportsApi: SportsApi
    ): SportsRepository = SportsRepositoryImpl(sportsApi)
}