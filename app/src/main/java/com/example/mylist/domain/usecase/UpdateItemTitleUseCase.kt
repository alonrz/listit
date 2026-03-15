package com.example.mylist.domain.usecase

import com.example.mylist.domain.repository.ListItemRepository
import javax.inject.Inject

class UpdateItemTitleUseCase @Inject constructor(private val repository: ListItemRepository) {
    suspend operator fun invoke(id: String, title: String) = repository.updateTitle(id, title)
}
