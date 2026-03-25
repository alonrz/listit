package com.example.listit.domain.usecase

import com.example.listit.domain.model.GroupOfItems
import com.example.listit.domain.repository.ListRepository
import java.util.UUID
import javax.inject.Inject

class AddListUseCase @Inject constructor(private val repository: ListRepository) {
    suspend operator fun invoke(name: String) {
        repository.insert(GroupOfItems(id = UUID.randomUUID().toString(), name = name))
    }
}