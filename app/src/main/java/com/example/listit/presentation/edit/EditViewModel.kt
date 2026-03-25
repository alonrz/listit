package com.example.listit.presentation.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listit.domain.usecase.DeleteItemUseCase
import com.example.listit.domain.usecase.UpdateItemTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateItemTitleUseCase: UpdateItemTitleUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
) : ViewModel() {

    val itemId: String = savedStateHandle["id"] ?: ""
    var itemTitle: MutableState<String> = mutableStateOf(savedStateHandle["title"] ?: "")
    val isDone: MutableState<Boolean> = mutableStateOf(savedStateHandle["isDone"] ?: false)

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
