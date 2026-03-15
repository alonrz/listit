package com.example.mylist.domain.repository

import com.example.mylist.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

interface ListItemRepository {
    fun observeAll(): Flow<List<ListItem>>
    suspend fun findById(id: String): ListItem
    suspend fun insert(item: ListItem)
    suspend fun deleteById(id: String)
    suspend fun updateTitle(id: String, title: String)
    suspend fun updateStatus(id: String, isDone: Boolean)
}
