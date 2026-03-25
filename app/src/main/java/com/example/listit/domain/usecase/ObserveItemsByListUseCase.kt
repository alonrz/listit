package com.example.listit.domain.usecase

import com.example.listit.domain.model.ListItem
import com.example.listit.domain.repository.ListItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveItemsByListUseCase @Inject constructor(private val repository: ListItemRepository) {
    operator fun invoke(listId: String): Flow<List<ListItem>> = repository.observeByListId(listId)
}