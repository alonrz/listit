package com.example.mylist.data

import androidx.lifecycle.LifecycleOwner
import com.example.mylist.models.ListItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainListRepoFakeData(
    private val lifecycleOwner: LifecycleOwner?,
) : ItemsRepo {
    private val _list = MutableStateFlow(
        listOf(
            ListItemData(title = getFakeNewTitle()),
            ListItemData(title = getFakeNewTitle()),
            ListItemData(title = getFakeNewTitle()),
            ListItemData(title = getFakeNewTitle()),
            ListItemData(title = getFakeNewTitle()),
        )
    )

    override fun getAll(): List<ListItemData> {
        return _list.value
    }

    override fun observeAll(): Flow<List<ListItemData>> {
        return _list.asStateFlow()
    }

    override suspend fun findById(id: String): ListItemData {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: ListItemData) {
//        _list.value?.add(item)
    }

    override fun delete(item: ListItemData) {
        TODO("Not yet implemented")
    }

    override fun deleteById(itemId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTitle(id: String, title: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateStatus(id: String, isDone: Boolean) {
        TODO("Not yet implemented")
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