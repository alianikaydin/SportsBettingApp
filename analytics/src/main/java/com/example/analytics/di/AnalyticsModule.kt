package com.example.analytics.di

import com.example.analytics.AnalyticsManager
import com.example.analytics.FirebaseAnalyticManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseAnalyticsManager(
        impl: FirebaseAnalyticManagerImpl
    ): AnalyticsManager
}