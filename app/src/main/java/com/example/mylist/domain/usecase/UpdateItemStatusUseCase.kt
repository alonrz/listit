package com.example.mylist.domain.usecase

import com.example.mylist.domain.repository.ListItemRepository
import javax.inject.Inject

class UpdateItemStatusUseCase @Inject constructor(private val repository: ListItemRepository) {
    suspend operator fun invoke(id: String, isDone: Boolean) = repository.updateStatus(id, isDone)
}
