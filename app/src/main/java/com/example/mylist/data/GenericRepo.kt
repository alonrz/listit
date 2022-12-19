package com.example.mylist.data

import androidx.lifecycle.LiveData
import com.example.mylist.models.ListItemData

interface GenericRepo {
    fun getAll(): List<ListItemData>

    fun observeAll(): LiveData<List<ListItemData>>

    fun findById(id: Int): ListItemData

    suspend fun insert(item: ListItemData)

    fun delete(listItemData: ListItemData)

    fun deleteById(itemId: String)
}