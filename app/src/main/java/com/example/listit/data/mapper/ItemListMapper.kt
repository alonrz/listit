package com.example.listit.data.mapper

import com.example.listit.data.local.ItemListEntity
import com.example.listit.domain.model.GroupOfItems

fun ItemListEntity.toDomain(): GroupOfItems = GroupOfItems(id = id, name = name, colorIndex = colorIndex)

fun GroupOfItems.toEntity(): ItemListEntity = ItemListEntity(id = id, name = name, colorIndex = colorIndex)