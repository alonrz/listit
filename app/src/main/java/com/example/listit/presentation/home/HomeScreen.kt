package com.example.listit.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.listit.domain.model.ListItem
import com.example.listit.presentation.navigation.ScreenNavigation
import com.example.listit.ui.theme.ListItTheme
import com.example.listit.presentation.components.ListItemView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainListView(
    viewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val listItems by viewModel.observeItems.collectAsStateWithLifecycle()

    MainListViewInternal(
        listItems = listItems,
        modifier = modifier,
        onItemClick = { id: String, title: String, isDone: Boolean ->
            navController.navigate(
                route = ScreenNavigation.Edit.passIdAndTitle(
                    id = id,
                    title = title,
                    isDone = isDone,
                )
            )
        },
        onCheckedClick = { id: String, isDone: Boolean ->
            viewModel.changeItemCheckStatus(id, checked = isDone.not())
        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainListViewInternal(
    modifier: Modifier = Modifier,
    listItems: List<ListItem>,
    onItemClick: (id: String, title: String, isDone: Boolean) -> Unit,
    onCheckedClick: (id: String, isDone: Boolean) -> Unit,
) {
    var onlyShowDone by remember { mutableStateOf(false) }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = listItems.filter {
            it.isDone == onlyShowDone
        }) { item: ListItem ->
            ListItemView(
                title = item.title,
                isDone = item.isDone,
                onCheckedClick = {
                    onCheckedClick(
                        item.id,
                        item.isDone,
                    )
                },
                onItemClick = {
                    onItemClick(
                        item.id,
                        item.title,
                        item.isDone,
                    )
                }
            )
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TextButton(onClick = { onlyShowDone = !onlyShowDone }) {
                    Text(text = if (onlyShowDone) "Show not completed" else "Show completed")
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
fun MainListViewPreview() {
    ListItTheme {
        MainListViewInternal(
            listItems = listOf(
                ListItem(id = "1", title = "Buy groceries", isDone = false),
                ListItem(id = "2", title = "Walk the dog", isDone = true),
                ListItem(id = "3", title = "Finish homework", isDone = false),
                ListItem(id = "4", title = "Call mom", isDone = false),
                ListItem(id = "5", title = "Read a book", isDone = true),
            ),
            onItemClick = { _, _, _ -> },
            onCheckedClick = { _, _ -> }
        )
    }
}
