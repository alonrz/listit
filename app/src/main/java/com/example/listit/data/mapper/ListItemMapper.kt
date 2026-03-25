package com.example.listit.data.mapper

import com.example.listit.domain.model.ListItem
import com.example.listit.data.local.ListItemEntity

fun ListItemEntity.toDomain(): ListItem = ListItem(id = id, title = title, isDone = isDone, listId = listId)

fun ListItem.toEntity(): ListItemEntity = ListItemEntity(id = id, title = title, isDone = isDone, listId = listId)