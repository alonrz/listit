package com.example.listit.domain.usecase

import com.example.listit.domain.repository.ListItemRepository
import javax.inject.Inject

class UpdateItemTitleUseCase @Inject constructor(private val repository: ListItemRepository) {
    suspend operator fun invoke(id: String, title: String) = repository.updateTitle(id, title)
}
