package com.example.listit.domain.usecase

import com.example.listit.domain.model.GroupOfItems
import com.example.listit.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveGroupsUseCase @Inject constructor(private val repository: ListRepository) {
    operator fun invoke(): Flow<List<GroupOfItems>> = repository.observeAll()
}