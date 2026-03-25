package com.example.listit.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.listit.domain.model.ListItem
import com.example.listit.presentation.components.GroupCardView
import com.example.listit.presentation.components.ListCardColor
import com.example.listit.presentation.components.ListCardIcon
import com.example.listit.presentation.components.ListCardStyle
import com.example.listit.ui.theme.ListItTheme

@Composable
fun HomeView(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onAddItemClick: (groupId: String) -> Unit = {},
) {
    val groupCards by viewModel.groupCards.collectAsStateWithLifecycle()

    HomeViewInternal(
        groupCards = groupCards,
        modifier = modifier,
        onAddItemClick = onAddItemClick,
        onViewAllClick = { /* future work */ },
        onCheckedClick = { id, isDone ->
            viewModel.changeItemCheckStatus(id, checked = isDone.not())
        },
    )
}

@Composable
private fun HomeViewInternal(
    groupCards: List<GroupCardUiState>,
    modifier: Modifier = Modifier,
    onAddItemClick: (groupId: String) -> Unit,
    onViewAllClick: (groupId: String) -> Unit,
    onCheckedClick: (id: String, isDone: Boolean) -> Unit,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = groupCards, key = { it.groupId }) { card ->
            GroupCardView(
                listName = card.groupName,
                items = card.items,
                style = card.style,
                onAddItemClick = { onAddItemClick(card.groupId) },
                onViewAllClick = { onViewAllClick(card.groupId) },
                onCheckedClick = onCheckedClick,
            )
        }
    }
}

@Composable
@PreviewLightDark
fun HomeViewPreview() {
    ListItTheme {
        HomeViewInternal(
            groupCards = listOf(
                GroupCardUiState(
                    groupId = "1",
                    groupName = "Work",
                    items = listOf(
                        ListItem(id = "1", title = "Review PR", isDone = false),
                        ListItem(id = "2", title = "Email client", isDone = true),
                    ),
                    style = ListCardStyle(
                        icon = ListCardIcon.WORK,
                        accentColor = ListCardColor.BLUE,
                    ),
                ),
                GroupCardUiState(
                    groupId = "2",
                    groupName = "Shopping",
                    items = listOf(
                        ListItem(id = "3", title = "Buy groceries", isDone = false),
                        ListItem(id = "4", title = "Pick up dry cleaning", isDone = false),
                    ),
                    style = ListCardStyle(
                        icon = ListCardIcon.SHOPPING,
                        accentColor = ListCardColor.GREEN,
                    ),
                ),
            ),
            onAddItemClick = {},
            onViewAllClick = {},
            onCheckedClick = { _, _ -> },
        )
    }
}
