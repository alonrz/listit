package com.example.mylist.domain.usecase

import com.example.mylist.domain.model.ListItem
import com.example.mylist.domain.repository.ListItemRepository
import kotlinx.coroutines.flow.Flow

class ObserveItemsUseCase(private val repository: ListItemRepository) {
    operator fun invoke(): Flow<List<ListItem>> = repository.observeAll()
}
