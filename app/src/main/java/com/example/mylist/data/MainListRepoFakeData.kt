package com.example.mylist.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mylist.models.ListItemData

class MainListRepoFakeData(
    private val lifecycleOwner: LifecycleOwner?,
) : GenericRepo {
    private val _list = MutableLiveData(
        listOf(
            ListItemData(title = getFakeNewTitle()),
            ListItemData(title = getFakeNewTitle()),
            ListItemData(title = getFakeNewTitle()),
            ListItemData(title = getFakeNewTitle()),
            ListItemData(title = getFakeNewTitle()),
        )
    )

    override fun getAll(): List<ListItemData> {
        return _list.value ?: emptyList()
    }

    private val listItemDataObserver = Observer<List<ListItemData>> {
//        it.
    }

    override fun observeAll(): LiveData<List<ListItemData>> {
        return _list
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