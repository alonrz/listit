package com.example.mylist.di

import android.content.Context
import com.example.mylist.data.local.AppDatabase
import com.example.mylist.data.repository.ListItemRepositoryImpl
import com.example.mylist.domain.repository.ListItemRepository
import com.example.mylist.domain.usecase.AddItemUseCase
import com.example.mylist.domain.usecase.DeleteItemUseCase
import com.example.mylist.domain.usecase.ObserveItemsUseCase
import com.example.mylist.domain.usecase.UpdateItemStatusUseCase
import com.example.mylist.domain.usecase.UpdateItemTitleUseCase

class AppContainer(context: Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.mainListDao()
    private val repository: ListItemRepository = ListItemRepositoryImpl(dao)

    val observeItems = ObserveItemsUseCase(repository)
    val addItem = AddItemUseCase(repository)
    val deleteItem = DeleteItemUseCase(repository)
    val updateItemTitle = UpdateItemTitleUseCase(repository)
    val updateItemStatus = UpdateItemStatusUseCase(repository)
}
