package com.example.listit.presentation.home

import com.example.listit.domain.model.ListItem
import com.example.listit.presentation.components.ListCardStyle

data class GroupCardUiState(
    val groupId: String,
    val groupName: String,
    val items: List<ListItem>,
    val style: ListCardStyle,
)
