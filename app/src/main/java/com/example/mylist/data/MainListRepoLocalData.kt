package com.example.mylist.data

import androidx.lifecycle.LiveData
import com.example.mylist.models.ListItemData

/*
Following repository pattern in article https://medium.com/swlh/repository-pattern-in-android-c31d0268118c
 */
class MainListRepoLocalData(private val mainListDAO: MainListDao) : GenericRepo {
    override fun getAll(): List<ListItemData> {
        return mainListDAO.getAll()
    }

    override fun observeAll(): LiveData<List<ListItemData>> {
        return mainListDAO.observeAll()
    }

    override suspend fun findById(id: String): ListItemData {
        return mainListDAO.findById(id)
    }

    override suspend fun insert(item: ListItemData) {
        mainListDAO.insert(item)
    }

    override fun delete(listItemData: ListItemData) {
        mainListDAO.delete(listItemData)
    }

    override fun deleteById(itemId: String) {
        mainListDAO.deleteById(itemId)
    }

    override suspend fun updateTitle(id: String, title: String) {
        mainListDAO.updateTitle(id, title)
    }

    override suspend fun updateStatus(id: String, isDone: Boolean) {
        mainListDAO.updateStatus(id, isDone)
    }
}
