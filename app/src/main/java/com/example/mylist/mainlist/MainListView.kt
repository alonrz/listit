package com.example.mylist.mainlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.mylist.models.ListItemData
import com.example.mylist.navigation.ScreenNavigation
import com.example.mylist.views.ListItemView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainListView(
    viewModel: MainListViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val listItems by viewModel.observeItems.observeAsState(initial = emptyList())
    var onlyShowDone by remember { mutableStateOf(false) }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = listItems.filter {
            it.isDone == onlyShowDone
        }) { item: ListItemData ->
            ListItemView(
                title = item.title,
                id = item.id,
                isDone = item.isDone,
                onCheckedClick = { viewModel.changeItemCheckStatus(item.id, !item.isDone) },
                onItemClick = {
                    navController.navigate(
                        route = ScreenNavigation.Edit.passIdAndTitle(
                            id = item.id,
                            title = item.title,
                            isDone = item.isDone,
                        )
                    )
                })
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
            {
                TextButton(onClick = { onlyShowDone = !onlyShowDone }) {
                    Text(text = if (onlyShowDone) "Show not completed" else "Show completed")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainListViewPreview() {
//    MainListView(items = listOf(
//        ListItemData(title = "a", id = 1),
//        ListItemData(title = "b", id = 2),
//        ListItemData(title = "c", id = 3),
//    ),
//        navController = rememberNavController(), onCheckedClick = { _, _ -> })
}