package com.example.mylist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ListItemEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainListDao(): MainListDao
}
