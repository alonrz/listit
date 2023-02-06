package com.example.mylist.mainlist

import android.app.Application
import androidx.lifecycle.*
import com.example.mylist.data.*
import com.example.mylist.models.ListItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainListViewModel(
    application: Application,
    private val repo: GenericRepo,
    private val savedStateHandle: SavedStateHandle,
    lifecycle: Lifecycle,
) : AndroidViewModel(application) {
    private val _forceUpdate = MutableLiveData<Boolean>(false)
    private val _dataLoading = MutableLiveData<Boolean>(false)
    val observeItems: LiveData<List<ListItemData>> = repo.observeAll()

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

    override fun onCleared() {
        super.onCleared()
//        repo.observeAll().removeObserver(observeItems)
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