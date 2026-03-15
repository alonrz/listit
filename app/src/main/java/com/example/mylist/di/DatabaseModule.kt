package com.example.mylist.di

import android.content.Context
import androidx.room.Room
import com.example.mylist.data.local.AppDatabase
import com.example.mylist.data.local.MainListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "main_list"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMainListDao(database: AppDatabase): MainListDao =
        database.mainListDao()
}
