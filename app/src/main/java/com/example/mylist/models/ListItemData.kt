package com.example.mylist.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "main_list")
data class ListItemData(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo val title: String,
    @ColumnInfo val isDone: Boolean = false,
)