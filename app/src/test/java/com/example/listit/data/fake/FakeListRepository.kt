package com.example.listit.data.fake

import com.example.listit.domain.model.DEFAULT_LIST_ID
import com.example.listit.domain.model.DEFAULT_LIST_NAME
import com.example.listit.domain.model.GroupOfItems
import com.example.listit.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeListRepository : ListRepository {
    private val _lists = MutableStateFlow(
        listOf(GroupOfItems(id = DEFAULT_LIST_ID, name = DEFAULT_LIST_NAME))
    )

    override fun observeAll(): Flow<List<GroupOfItems>> = _lists.asStateFlow()

    override suspend fun findById(id: String): GroupOfItems =
        _lists.value.first { it.id == id }

    override suspend fun insert(list: GroupOfItems) {
        _lists.value += list
    }

    override suspend fun deleteById(id: String) {
        _lists.value = _lists.value.filter { it.id != id }
    }

    override suspend fun updateName(id: String, name: String) {
        _lists.value = _lists.value.map { if (it.id == id) it.copy(name = name) else it }
    }
}