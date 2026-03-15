package com.example.mylist.domain.model

data class ListItem(
    val id: String,
    val title: String,
    val isDone: Boolean = false,
)
