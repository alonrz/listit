package com.example.mylist.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mylist.domain.model.DEFAULT_LIST_ID
import java.util.*

@Entity(
    tableName = "main_list",
    foreignKeys = [
        ForeignKey(
            entity = ItemListEntity::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("listId")]
)
data class ListItemEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo val title: String,
    @ColumnInfo val isDone: Boolean = false,
    @ColumnInfo val listId: String = DEFAULT_LIST_ID,
)