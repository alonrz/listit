package com.example.mylist.di

import com.example.mylist.data.repository.ItemListRepositoryImpl
import com.example.mylist.data.repository.ListItemRepositoryImpl
import com.example.mylist.domain.repository.ItemListRepository
import com.example.mylist.domain.repository.ListItemRepository
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
    abstract fun bindItemListRepository(impl: ItemListRepositoryImpl): ItemListRepository
}