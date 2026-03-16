package com.example.mylist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemListDao {
    @Query("SELECT * FROM item_lists")
    fun observeAll(): Flow<List<ItemListEntity>>

    @Query("SELECT * FROM item_lists WHERE id = :id")
    suspend fun findById(id: String): ItemListEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: ItemListEntity)

    @Query("DELETE FROM item_lists WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE item_lists SET name = :name WHERE id = :id")
    suspend fun updateName(id: String, name: String)
}