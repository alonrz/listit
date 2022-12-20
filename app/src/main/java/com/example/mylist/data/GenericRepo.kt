package com.example.mylist.data

import androidx.lifecycle.LiveData
import com.example.mylist.models.ListItemData

interface GenericRepo {
    fun getAll(): List<ListItemData>

    fun observeAll(): LiveData<List<ListItemData>>

    suspend fun findById(id: String): ListItemData

    suspend fun insert(item: ListItemData)

    fun delete(listItemData: ListItemData)

    fun deleteById(itemId: String)

    suspend fun updateTitle(id: String, title:String)
}