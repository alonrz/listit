package com.example.mylist.domain.usecase

import com.example.mylist.domain.repository.ItemListRepository
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(private val repository: ItemListRepository) {
    suspend operator fun invoke(id: String) = repository.deleteById(id)
}