package com.example.mylist.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import com.example.mylist.data.ItemsRepo
import com.example.mylist.models.ListItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel(
    val itemId: String,
    var itemTitle: MutableState<String> = mutableStateOf(""),
    val isDone: MutableState<Boolean> = mutableStateOf(false),
    private val repo: ItemsRepo,
    private val lifecycle: Lifecycle,
) : ViewModel() {

    private lateinit var item: ListItemData

    init {
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            item = repo.findById(id = itemId)
        }
    }

    // Move this to a save button
    fun changeItemTitle(title: String) {
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            repo.updateTitle(item.id ?: itemId, title = title)
        }
    }

    fun deleteItem() {
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            repo.deleteById(item.id)
        }
    }
}