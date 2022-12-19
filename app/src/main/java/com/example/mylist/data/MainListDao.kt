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
    fun findById(id: Int): ListItemData

    @Insert
    fun insert(item: ListItemData)

    @Delete
    fun delete(item: ListItemData)

    @Query("DELETE FROM main_list WHERE id == :id")
    fun deleteById(id: String)
}