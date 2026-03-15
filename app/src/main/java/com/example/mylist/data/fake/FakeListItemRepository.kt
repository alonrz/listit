package com.example.mylist.data.fake

import com.example.mylist.domain.model.ListItem
import com.example.mylist.domain.repository.ListItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class FakeListItemRepository : ListItemRepository {
    private val _list = MutableStateFlow(
        listOf(
            ListItem(id = UUID.randomUUID().toString(), title = getFakeNewTitle()),
            ListItem(id = UUID.randomUUID().toString(), title = getFakeNewTitle()),
            ListItem(id = UUID.randomUUID().toString(), title = getFakeNewTitle()),
            ListItem(id = UUID.randomUUID().toString(), title = getFakeNewTitle()),
            ListItem(id = UUID.randomUUID().toString(), title = getFakeNewTitle()),
        )
    )

    override fun observeAll(): Flow<List<ListItem>> {
        return _list.asStateFlow()
    }

    override suspend fun findById(id: String): ListItem {
        return _list.value.first { it.id == id }
    }

    override suspend fun insert(item: ListItem) {
        _list.value = _list.value + item
    }

    override suspend fun deleteById(id: String) {
        _list.value = _list.value.filter { it.id != id }
    }

    override suspend fun updateTitle(id: String, title: String) {
        _list.value = _list.value.map { if (it.id == id) it.copy(title = title) else it }
    }

    override suspend fun updateStatus(id: String, isDone: Boolean) {
        _list.value = _list.value.map { if (it.id == id) it.copy(isDone = isDone) else it }
    }

    companion object {
        fun getFakeNewTitle(): String {
            return listOf(
                "Wash dishes",
                "Do laundry",
                "Change lights",
                "Make coffee",
                "Go shopping",
                "Take a walk",
                "Make your bed",
                "Draw a picture",
                "Meditate",
                "Go for a run",
                "Take a break"
            ).random()
        }
    }
}
