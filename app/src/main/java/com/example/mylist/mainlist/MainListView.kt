package com.example.mylist.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mylist.models.ListItemData
import com.example.mylist.navigation.ScreenNavigation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainListView(
    items: List<ListItemData>,
    navController: NavController,
    onCheckedClick: (Int, Boolean) -> Unit,
) {
    LazyColumn() {
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
        items(items, key = { it.id }) { item ->
            var isChecked by rememberSaveable { mutableStateOf(item.isChecked) }
            ListItemView(
                title = "${item.name} (${item.id})",
                id = item.id,
                isChecked = isChecked,
                onCheckedClick = { b ->
                    onCheckedClick(item.id, b)
                    isChecked = b
                },
                onItemClick = {
                    navController.navigate(route = ScreenNavigation.Edit.route)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainListViewPreview() {
    MainListView(items = listOf(
        ListItemData(name = "a", id = 1),
        ListItemData(name = "b", id = 2),
        ListItemData(name = "c", id = 3),
    ),
        navController = rememberNavController(), onCheckedClick = { _, _ -> })
}