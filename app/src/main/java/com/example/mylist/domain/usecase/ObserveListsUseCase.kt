package com.example.mylist.domain.usecase

import com.example.mylist.domain.model.ItemList
import com.example.mylist.domain.repository.ItemListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveListsUseCase @Inject constructor(private val repository: ItemListRepository) {
    operator fun invoke(): Flow<List<ItemList>> = repository.observeAll()
}