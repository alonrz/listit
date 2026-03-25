package com.example.listit.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.listit.data.local.AppDatabase
import com.example.listit.data.local.ItemListDao
import com.example.listit.data.local.MainListDao
import com.example.listit.domain.model.DEFAULT_LIST_ID
import com.example.listit.domain.model.DEFAULT_LIST_NAME
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
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL(
                        "INSERT OR IGNORE INTO item_lists (id, name) VALUES ('$DEFAULT_LIST_ID', '$DEFAULT_LIST_NAME')"
                    )
                }
            })
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMainListDao(database: AppDatabase): MainListDao =
        database.mainListDao()

    @Provides
    fun provideItemListDao(database: AppDatabase): ItemListDao =
        database.itemListDao()
}