package com.example.listit.di

import com.example.listit.data.repository.ListRepositoryImpl
import com.example.listit.data.repository.ListItemRepositoryImpl
import com.example.listit.domain.repository.ListRepository
import com.example.listit.domain.repository.ListItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindListItemRepository(impl: ListItemRepositoryImpl): ListItemRepository

    @Binds
    @Singleton
    abstract fun bindItemListRepository(impl: ListRepositoryImpl): ListRepository
}