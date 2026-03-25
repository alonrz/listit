package com.example.listit.domain.usecase

import com.example.listit.domain.repository.ListItemRepository
import javax.inject.Inject

class UpdateItemStatusUseCase @Inject constructor(private val repository: ListItemRepository) {
    suspend operator fun invoke(id: String, isDone: Boolean) = repository.updateStatus(id, isDone)
}
