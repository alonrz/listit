package com.example.listit.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listit.domain.usecase.AddItemUseCase
import com.example.listit.domain.usecase.AddListUseCase
import com.example.listit.domain.usecase.DeleteItemUseCase
import com.example.listit.domain.usecase.ObserveGroupsUseCase
import com.example.listit.domain.usecase.ObserveItemsUseCase
import com.example.listit.domain.usecase.MoveItemUseCase
import com.example.listit.domain.usecase.UpdateItemStatusUseCase
import com.example.listit.domain.usecase.UpdateItemTitleUseCase
import com.example.listit.presentation.components.ListCardColor
import com.example.listit.presentation.components.ListCardIcon
import com.example.listit.presentation.components.ListCardStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    observeItemsUseCase: ObserveItemsUseCase,
    observeGroupsUseCase: ObserveGroupsUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val addListUseCase: AddListUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val updateItemStatusUseCase: UpdateItemStatusUseCase,
    private val updateItemTitleUseCase: UpdateItemTitleUseCase,
    private val moveItemUseCase: MoveItemUseCase,
) : ViewModel() {

    val groupCards: StateFlow<List<GroupCardUiState>> = combine(
        observeGroupsUseCase(),
        observeItemsUseCase(),
    ) { groups, items ->
        val itemsByListId = items.groupBy { it.listId }
        val icons = ListCardIcon.entries
        val colors = ListCardColor.entries

        groups.sortedBy { it.id }
            .map { group ->
                GroupCardUiState(
                    groupId = group.id,
                    groupName = group.name,
                    items = itemsByListId[group.id].orEmpty()
                        .sortedBy { it.isDone },
                    style = ListCardStyle(
                        icon = icons[group.colorIndex % icons.size],
                        accentColor = colors[group.colorIndex % colors.size],
                    ),
                )
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList(),
    )

    fun addItem(title: String, listId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addItemUseCase(title = title, listId = listId)
        }
    }

    fun addGroup(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addListUseCase(name)
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

    fun updateItem(id: String, title: String, groupId: String, isDone: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateItemTitleUseCase(id = id, title = title)
            updateItemStatusUseCase(id = id, isDone = isDone)
            moveItemUseCase(id = id, targetListId = groupId)
        }
    }
}
