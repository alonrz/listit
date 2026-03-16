package com.example.mylist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MainListDao {
    @Query("SELECT * FROM main_list")
    fun observeAll(): Flow<List<ListItemEntity>>

    @Query("SELECT * FROM main_list WHERE listId = :listId")
    fun observeByListId(listId: String): Flow<List<ListItemEntity>>

    @Query("SELECT * FROM main_list WHERE isDone = 1")
    fun observeAllDone(): Flow<List<ListItemEntity>>

    @Query("SELECT * FROM main_list WHERE isDone = 0")
    fun observeAllNotDone(): Flow<List<ListItemEntity>>

    @Query("SELECT * FROM main_list WHERE id = :id")
    suspend fun findById(id: String): ListItemEntity

    @Insert
    suspend fun insert(item: ListItemEntity)

    @Delete
    suspend fun delete(item: ListItemEntity)

    @Query("DELETE FROM main_list WHERE id == :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM main_list WHERE listId = :listId")
    suspend fun deleteByListId(listId: String)

    @Query("UPDATE main_list SET title = :title WHERE id == :id")
    suspend fun updateTitle(id: String, title: String)

    @Query("UPDATE main_list SET isDone = :isDone WHERE id == :id")
    suspend fun updateStatus(id: String, isDone: Boolean)

    @Query("UPDATE main_list SET listId = :listId WHERE id = :id")
    suspend fun moveToList(id: String, listId: String)
}