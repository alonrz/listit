package com.example.mylist.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylist.MainListViewModel
import com.example.mylist.models.ListItemData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainListView(items: List<ListItemData>) {
    LazyColumn() {
        stickyHeader {
            Text(text = "Main List",
            color = Color.White,
            modifier = Modifier.background(Color.Gray).padding(4.dp).fillMaxWidth())
        }
        items(items) { item ->
            ListItemView(title = item.name, status = item.status)
        }
    }
}