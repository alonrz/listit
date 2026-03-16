package com.example.mylist.data.repository

import com.example.mylist.data.local.MainListDao
import com.example.mylist.data.mapper.toDomain
import com.example.mylist.data.mapper.toEntity
import com.example.mylist.domain.model.ListItem
import com.example.mylist.domain.repository.ListItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListItemRepositoryImpl @Inject constructor(private val dao: MainListDao) : ListItemRepository {

    override fun observeAll(): Flow<List<ListItem>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override fun observeByListId(listId: String): Flow<List<ListItem>> =
        dao.observeByListId(listId).map { list -> list.map { it.toDomain() } }

    override suspend fun findById(id: String): ListItem =
        dao.findById(id).toDomain()

    override suspend fun insert(item: ListItem) =
        dao.insert(item.toEntity())

    override suspend fun deleteById(id: String) =
        dao.deleteById(id)

    override suspend fun deleteByListId(listId: String) =
        dao.deleteByListId(listId)

    override suspend fun updateTitle(id: String, title: String) =
        dao.updateTitle(id, title)

    override suspend fun updateStatus(id: String, isDone: Boolean) =
        dao.updateStatus(id, isDone)

    override suspend fun moveToList(id: String, listId: String) =
        dao.moveToList(id, listId)
}