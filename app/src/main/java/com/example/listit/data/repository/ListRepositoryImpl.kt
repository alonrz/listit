package com.example.listit.data.repository

import com.example.listit.data.local.ItemListDao
import com.example.listit.data.mapper.toDomain
import com.example.listit.data.mapper.toEntity
import com.example.listit.domain.model.GroupOfItems
import com.example.listit.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(private val dao: ItemListDao) : ListRepository {

    override fun observeAll(): Flow<List<GroupOfItems>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun findById(id: String): GroupOfItems =
        dao.findById(id).toDomain()

    override suspend fun insert(list: GroupOfItems) =
        dao.insert(list.toEntity())

    override suspend fun deleteById(id: String) =
        dao.deleteById(id)

    override suspend fun updateName(id: String, name: String) =
        dao.updateName(id, name)

    override suspend fun getUsedColorIndices(): List<Int> =
        dao.getAllColorIndices()
}