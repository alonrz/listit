package com.example.mylist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylist.domain.model.ListItem
import com.example.mylist.domain.usecase.AddItemUseCase
import com.example.mylist.domain.usecase.DeleteItemUseCase
import com.example.mylist.domain.usecase.ObserveItemsUseCase
import com.example.mylist.domain.usecase.UpdateItemStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    observeItemsUseCase: ObserveItemsUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val updateItemStatusUseCase: UpdateItemStatusUseCase,
) : ViewModel() {

    val observeItems: StateFlow<List<ListItem>> = observeItemsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addItem(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addItemUseCase(title)
        }
    }

    fun removeItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteItemUseCase(id)
        }
    }

    fun changeItemCheckStatus(id: String, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateItemStatusUseCase(id = id, isDone = checked)
        }
    }
}
