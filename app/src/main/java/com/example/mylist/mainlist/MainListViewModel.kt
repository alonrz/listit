package com.example.mylist.mainlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.mylist.data.ItemsRepo
import com.example.mylist.data.MainListRepoFakeData
import com.example.mylist.models.ListItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainListViewModel(
    application: Application,
    private val repo: ItemsRepo,
    private val savedStateHandle: SavedStateHandle,
    lifecycle: Lifecycle,
) : AndroidViewModel(application) {
    private val _forceUpdate = MutableStateFlow(false)
    val forceUpdate: StateFlow<Boolean> = _forceUpdate.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading: StateFlow<Boolean> = _dataLoading.asStateFlow()

    val observeItems: StateFlow<List<ListItemData>> = repo.observeAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        _forceUpdate.value = true
    }

    fun addItem(item: ListItemData? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insert(
                item ?: ListItemData(
                    title = MainListRepoFakeData.getFakeNewTitle(),
                    isDone = false,
                )
            )
        }
    }

    fun removeItem(id: String) {
        repo.deleteById(id)
    }

    fun changeItemCheckStatus(id: String, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateStatus(id = id, isDone = checked)
        }
    }
}