package com.example.mylist.presentation.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylist.domain.usecase.DeleteItemUseCase
import com.example.mylist.domain.usecase.UpdateItemTitleUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel(
    val itemId: String,
    var itemTitle: MutableState<String> = mutableStateOf(""),
    val isDone: MutableState<Boolean> = mutableStateOf(false),
    private val updateItemTitleUseCase: UpdateItemTitleUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
) : ViewModel() {

    fun changeItemTitle(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateItemTitleUseCase(itemId, title = title)
        }
    }

    fun deleteItem() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteItemUseCase(itemId)
        }
    }
}
