package com.example.listit.domain.repository

import com.example.listit.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

interface ListItemRepository {
    fun observeAll(): Flow<List<ListItem>>
    fun observeByListId(listId: String): Flow<List<ListItem>>
    suspend fun findById(id: String): ListItem
    suspend fun insert(item: ListItem)
    suspend fun deleteById(id: String)
    suspend fun deleteByListId(listId: String)
    suspend fun updateTitle(id: String, title: String)
    suspend fun updateStatus(id: String, isDone: Boolean)
    suspend fun moveToList(id: String, listId: String)
}
