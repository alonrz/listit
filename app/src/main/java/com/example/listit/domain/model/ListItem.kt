package com.example.listit.domain.model

data class ListItem(
    val id: String,
    val title: String,
    val isDone: Boolean = false,
    val listId: String = DEFAULT_LIST_ID,
)
