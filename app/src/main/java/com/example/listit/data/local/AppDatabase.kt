package com.example.listit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ListItemEntity::class, ItemListEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainListDao(): MainListDao
    abstract fun itemListDao(): ItemListDao
}