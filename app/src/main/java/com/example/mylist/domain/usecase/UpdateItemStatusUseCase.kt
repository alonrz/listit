package com.example.mylist.domain.usecase

import com.example.mylist.domain.repository.ListItemRepository

class UpdateItemStatusUseCase(private val repository: ListItemRepository) {
    suspend operator fun invoke(id: String, isDone: Boolean) = repository.updateStatus(id, isDone)
}
