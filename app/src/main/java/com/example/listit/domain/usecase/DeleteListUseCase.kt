package com.example.listit.domain.usecase

import com.example.listit.domain.repository.ListRepository
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(private val repository: ListRepository) {
    suspend operator fun invoke(id: String) = repository.deleteById(id)
}