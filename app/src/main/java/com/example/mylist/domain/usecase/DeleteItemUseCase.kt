package com.example.mylist.domain.usecase

import com.example.mylist.domain.repository.ListItemRepository

class DeleteItemUseCase(private val repository: ListItemRepository) {
    suspend operator fun invoke(id: String) = repository.deleteById(id)
}
