package com.example.mylist.domain.repository

import com.example.mylist.domain.model.ItemList
import kotlinx.coroutines.flow.Flow

interface ItemListRepository {
    fun observeAll(): Flow<List<ItemList>>
    suspend fun findById(id: String): ItemList
    suspend fun insert(list: ItemList)
    suspend fun deleteById(id: String)
    suspend fun updateName(id: String, name: String)
}