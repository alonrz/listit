package com.example.mylist.data.mapper

import com.example.mylist.domain.model.ListItem
import com.example.mylist.data.local.ListItemEntity

fun ListItemEntity.toDomain(): ListItem = ListItem(id = id, title = title, isDone = isDone, listId = listId)

fun ListItem.toEntity(): ListItemEntity = ListItemEntity(id = id, title = title, isDone = isDone, listId = listId)