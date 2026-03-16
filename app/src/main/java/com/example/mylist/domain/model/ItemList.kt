package com.example.mylist.domain.model

data class ItemList(
    val id: String,
    val name: String,
)

const val DEFAULT_LIST_ID = "default"
const val DEFAULT_LIST_NAME = "Main"