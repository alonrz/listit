package com.example.listit.domain.usecase

import com.example.listit.domain.repository.ListItemRepository
import javax.inject.Inject

class MoveItemUseCase @Inject constructor(private val repository: ListItemRepository) {
    suspend operator fun invoke(id: String, targetListId: String) = repository.moveToList(id, targetListId)
}