package com.example.mylist.data

import com.example.mylist.models.ListItemData
import kotlinx.coroutines.flow.Flow

interface ItemsRepo {
    fun getAll(): List<ListItemData>

    fun observeAll(): Flow<List<ListItemData>>

    suspend fun findById(id: String): ListItemData

    suspend fun insert(item: ListItemData)

    fun delete(listItemData: ListItemData)

    fun deleteById(itemId: String)

    suspend fun updateTitle(id: String, title:String)

    suspend fun updateStatus(id: String, isDone: Boolean)
}