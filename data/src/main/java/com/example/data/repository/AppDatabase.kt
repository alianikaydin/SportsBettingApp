package com.example.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.repository.entities.CartEntity
import com.example.data.repository.model.CartDao

@Database(
    entities = [CartEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}