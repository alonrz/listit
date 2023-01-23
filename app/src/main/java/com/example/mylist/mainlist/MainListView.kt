package com.example.mylist.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mylist.mainlist.MainListViewModel
import com.example.mylist.models.ListItemData
import com.example.mylist.navigation.ScreenNavigation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainListView(
    viewModel: MainListViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val listItems by viewModel.observeItems.observeAsState(initial = emptyList())
//    var promptAddNewItemUi by remember { mutableStateOf(promptAddNewItemUi2) }
    Column {
        LazyColumn(modifier = modifier.weight(1f)) {
            stickyHeader {
                Text(
                    text = "Main List",
                    color = Color.White,
                    modifier = Modifier
                        .background(Color.Gray)
                        .padding(4.dp)
                        .fillMaxWidth()
                )
            }

            items(items = listItems) { item: ListItemData ->
                ListItemView(
                    title = item.title,
                    id = item.id,
                    isChecked = item.isDone,
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