package com.example.mylist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mylist.models.ListItemData

@Dao
interface MainListDao {
    @Query("SELECT * FROM main_list")
    fun observeAll(): LiveData<List<ListItemData>>

    @Query("SELECT * FROM main_list")
    fun getAll(): List<ListItemData>

    @Query("SELECT * FROM main_list WHERE id = :id")
    suspend fun findById(id: String): ListItemData

    @Insert
    fun insert(item: ListItemData)

    @Delete
    fun delete(item: ListItemData)

    @Query("DELETE FROM main_list WHERE id == :id")
    fun deleteById(id: String)

    @Query("UPDATE main_list SET title = :title WHERE id == :id")
    fun updateTitle(id: String, title: String)
}