package com.example.listit.domain.usecase

import com.example.listit.domain.repository.ListItemRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(private val repository: ListItemRepository) {
    suspend operator fun invoke(id: String) = repository.deleteById(id)
}
