package com.example.data.repository.di

import android.content.Context
import androidx.room.Room
import com.example.data.repository.AppDatabase
import com.example.data.repository.CartRepositoryImpl
import com.example.data.repository.SportsRepositoryImpl
import com.example.data.repository.model.CartDao
import com.example.domain.repository.CartRepository
import com.example.domain.repository.SportsRepository
import com.example.network.SportsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "betting_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartDao: CartDao
    ): CartRepository {
        return CartRepositoryImpl(cartDao)
    }

    @Provides
    @Singleton
    fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()
}