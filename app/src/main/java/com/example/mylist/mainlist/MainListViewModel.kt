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
    val observeItems: LiveData<List<ListItemData>>

    init {
        observeItems = repo.observeAll()
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
//        val item = repo.delete()_list.find { it.id == id }
//        item?.let { _list.remove(it) }
    }

    fun changeItemCheckStatus(id: String, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateStatus(id = id, isDone = checked)
        }
//        val item = _list.find { it.id == id }
//        val index = _list.indexOf(item)
//        item?.let {
//            _list.set(
//                index = index, element =
//                ListItemData(
//                    title = it.title,
//                    id = it.id,
//                    isDone = checked,
//                )
//            )
//        }
    }
}