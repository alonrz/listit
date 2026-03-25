package com.example.listit.domain.model

data class GroupOfItems(
    val id: String,
    val name: String,
)

const val DEFAULT_LIST_ID = "default"
const val DEFAULT_LIST_NAME = "Main"