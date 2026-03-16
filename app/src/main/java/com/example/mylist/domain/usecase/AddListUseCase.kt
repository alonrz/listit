package com.example.mylist.domain.usecase

import com.example.mylist.domain.model.ItemList
import com.example.mylist.domain.repository.ItemListRepository
import java.util.UUID
import javax.inject.Inject

class AddListUseCase @Inject constructor(private val repository: ItemListRepository) {
    suspend operator fun invoke(name: String) {
        repository.insert(ItemList(id = UUID.randomUUID().toString(), name = name))
    }
}