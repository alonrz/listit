package com.example.mylist.domain.usecase

import com.example.mylist.domain.model.DEFAULT_LIST_ID
import com.example.mylist.domain.model.ListItem
import com.example.mylist.domain.repository.ListItemRepository
import java.util.UUID
import javax.inject.Inject

class AddItemUseCase @Inject constructor(private val repository: ListItemRepository) {
    suspend operator fun invoke(title: String, listId: String = DEFAULT_LIST_ID) {
        repository.insert(ListItem(id = UUID.randomUUID().toString(), title = title, listId = listId))
    }
}