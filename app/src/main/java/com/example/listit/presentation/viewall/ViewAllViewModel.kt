package com.example.listit.presentation.viewall

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listit.domain.model.ListItem
import com.example.listit.domain.usecase.AddItemUseCase
import com.example.listit.domain.usecase.DeleteItemUseCase
import com.example.listit.domain.usecase.MoveItemUseCase
import com.example.listit.domain.usecase.ObserveGroupsUseCase
import com.example.listit.domain.usecase.ObserveItemsByListUseCase
import com.example.listit.domain.usecase.UpdateItemStatusUseCase
import com.example.listit.domain.usecase.UpdateItemTitleUseCase
import com.example.listit.presentation.components.ListCardColor
import com.example.listit.presentation.components.ListCardIcon
import com.example.listit.presentation.components.ListCardStyle
import com.example.listit.presentation.home.GroupCardUiState
import com.example.listit.presentation.navigation.VIEW_ALL_ARG_GROUP_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ViewAllUiState(
    val groupId: String = "",
    val groupName: String = "",
    val items: List<ListItem> = emptyList(),
    val style: ListCardStyle = ListCardStyle(
        icon = ListCardIcon.WORK,
        accentColor = ListCardColor.BLUE,
    ),
    val allGroups: List<GroupCardUiState> = emptyList(),
)

@HiltViewModel
class ViewAllViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeItemsByListUseCase: ObserveItemsByListUseCase,
    observeGroupsUseCase: ObserveGroupsUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val updateItemStatusUseCase: UpdateItemStatusUseCase,
    private val updateItemTitleUseCase: UpdateItemTitleUseCase,
    private val moveItemUseCase: MoveItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
) : ViewModel() {

    private val groupId: String = savedStateHandle.get<String>(VIEW_ALL_ARG_GROUP_ID) ?: ""

    val uiState: StateFlow<ViewAllUiState> = combine(
        observeGroupsUseCase(),
        observeItemsByListUseCase(groupId),
    ) { groups, items ->
        val icons = ListCardIcon.entries
        val colors = ListCardColor.entries
        val group = groups.find { it.id == groupId }

        val allGroupCards = groups.sortedBy { it.id }.map { g ->
            GroupCardUiState(
                groupId = g.id,
                groupName = g.name,
                items = emptyList(),
                style = ListCardStyle(
                    icon = icons[g.colorIndex % icons.size],
                    accentColor = colors[g.colorIndex % colors.size],
                ),
            )
        }

        ViewAllUiState(
            groupId = groupId,
            groupName = group?.name ?: "",
            items = items.sortedBy { it.isDone },
            style = ListCardStyle(
                icon = icons[(group?.colorIndex ?: 0) % icons.size],
                accentColor = colors[(group?.colorIndex ?: 0) % colors.size],
            ),
            allGroups = allGroupCards,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ViewAllUiState(groupId = groupId),
    )

    fun addItem(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addItemUseCase(title = title, listId = groupId)
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
            if (groupId != this@ViewAllViewModel.groupId) {
                moveItemUseCase(id = id, targetListId = groupId)
            }
        }
    }

    fun removeItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteItemUseCase(id)
        }
    }
}
