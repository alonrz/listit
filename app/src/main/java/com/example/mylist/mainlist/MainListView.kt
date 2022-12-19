package com.example.mylist.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylist.mainlist.MainListViewModel
import com.example.mylist.models.ListItemData
import com.example.mylist.navigation.ScreenNavigation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainListView(
    viewModel: MainListViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    onCheckedClick: (Int, Boolean) -> Unit,
) {
    val listItems by viewModel.observeItems.observeAsState(initial = emptyList())
    LazyColumn(modifier = modifier) {
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
        items(items = listItems) {
            item: ListItemData ->
            ListItemView(
                title = item.title,
                id = item.id,
                onCheckedClick = { } ,
                onItemClick = { /*TODO*/ })
        }
//        items(items = listItems) { item ->
//
//            var isChecked by rememberSaveable { mutableStateOf(item.isDone) }
//            ListItemView(
//                title = "${item.title} (${item.id})",
//                id = item.id,
//                isChecked = isChecked,
//                onCheckedClick = { b ->
//                    onCheckedClick(item.id, b)
//                    isChecked = b
//                },
//                onItemClick = {
//                    navController.navigate(route = ScreenNavigation.Edit.route)
//                }
//            )
//        }
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