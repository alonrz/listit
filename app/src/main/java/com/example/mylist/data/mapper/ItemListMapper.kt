package com.example.mylist.data.mapper

import com.example.mylist.data.local.ItemListEntity
import com.example.mylist.domain.model.ItemList

fun ItemListEntity.toDomain(): ItemList = ItemList(id = id, name = name)

fun ItemList.toEntity(): ItemListEntity = ItemListEntity(id = id, name = name)