package com.example.mylist.data.repository

import com.example.mylist.data.local.ItemListDao
import com.example.mylist.data.mapper.toDomain
import com.example.mylist.data.mapper.toEntity
import com.example.mylist.domain.model.ItemList
import com.example.mylist.domain.repository.ItemListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemListRepositoryImpl @Inject constructor(private val dao: ItemListDao) : ItemListRepository {

    override fun observeAll(): Flow<List<ItemList>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun findById(id: String): ItemList =
        dao.findById(id).toDomain()

    override suspend fun insert(list: ItemList) =
        dao.insert(list.toEntity())

    override suspend fun deleteById(id: String) =
        dao.deleteById(id)

    override suspend fun updateName(id: String, name: String) =
        dao.updateName(id, name)
}