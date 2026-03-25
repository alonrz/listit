package com.example.listit.domain.usecase

import com.example.listit.domain.model.ListItem
import com.example.listit.domain.repository.ListItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveItemsUseCase @Inject constructor(private val repository: ListItemRepository) {
    operator fun invoke(): Flow<List<ListItem>> = repository.observeAll()
}
