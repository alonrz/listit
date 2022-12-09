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
import androidx.compose.ui.unit.dp
import com.example.mylist.models.ListItemData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainListView(items: List<ListItemData>, onItemChecked: (Int, Boolean) -> Unit) {
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
        items(items) { item ->
            var isChecked by rememberSaveable { mutableStateOf(item.isChecked) }
            ListItemView(
                title = "${item.name} (${item.id})",
                id = item.id,
                isChecked = isChecked,
                onCheckedClick = { b ->
                    onItemChecked(item.id, b)
                    isChecked = b
                },
            )
        }
    }
}