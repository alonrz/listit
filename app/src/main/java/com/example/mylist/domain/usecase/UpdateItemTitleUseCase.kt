package com.example.mylist.domain.usecase

import com.example.mylist.domain.repository.ListItemRepository

class UpdateItemTitleUseCase(private val repository: ListItemRepository) {
    suspend operator fun invoke(id: String, title: String) = repository.updateTitle(id, title)
}
