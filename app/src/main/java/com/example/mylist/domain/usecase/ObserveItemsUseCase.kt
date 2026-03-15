package com.example.mylist.domain.usecase

import com.example.mylist.domain.model.ListItem
import com.example.mylist.domain.repository.ListItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveItemsUseCase @Inject constructor(private val repository: ListItemRepository) {
    operator fun invoke(): Flow<List<ListItem>> = repository.observeAll()
}
