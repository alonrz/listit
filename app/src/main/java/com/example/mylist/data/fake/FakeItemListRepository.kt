package com.example.mylist.data.fake

import com.example.mylist.domain.model.DEFAULT_LIST_ID
import com.example.mylist.domain.model.DEFAULT_LIST_NAME
import com.example.mylist.domain.model.ItemList
import com.example.mylist.domain.repository.ItemListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeItemListRepository : ItemListRepository {
    private val _lists = MutableStateFlow(
        listOf(ItemList(id = DEFAULT_LIST_ID, name = DEFAULT_LIST_NAME))
    )

    override fun observeAll(): Flow<List<ItemList>> = _lists.asStateFlow()

    override suspend fun findById(id: String): ItemList =
        _lists.value.first { it.id == id }

    override suspend fun insert(list: ItemList) {
        _lists.value += list
    }

    override suspend fun deleteById(id: String) {
        _lists.value = _lists.value.filter { it.id != id }
    }

    override suspend fun updateName(id: String, name: String) {
        _lists.value = _lists.value.map { if (it.id == id) it.copy(name = name) else it }
    }
}