package com.example.mylist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ListItemEntity::class, ItemListEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainListDao(): MainListDao
    abstract fun itemListDao(): ItemListDao
}