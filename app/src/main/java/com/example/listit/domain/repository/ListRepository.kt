package com.example.listit.domain.repository

import com.example.listit.domain.model.GroupOfItems
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    fun observeAll(): Flow<List<GroupOfItems>>
    suspend fun findById(id: String): GroupOfItems
    suspend fun insert(list: GroupOfItems)
    suspend fun deleteById(id: String)
    suspend fun updateName(id: String, name: String)
}